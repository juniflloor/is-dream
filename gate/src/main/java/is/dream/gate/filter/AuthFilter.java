package is.dream.gate.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import is.dream.common.Result;
import is.dream.common.exception.BaseExceptionCode;
import is.dream.common.utils.JWTIUtil;
import is.dream.gate.contants.URLConstant;
import is.dream.gate.fegin.AuthFegin;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 21:57
 * Authority authentication filter, this is through jwt and  redis start version to achieve High
 * availability of authentication.
 * First we start redis and save redis version into mysql and redis. once redis restart the redis
 * version increase one.
 * Second when user login system, we will create token, save into mysql and redis.meanwhile wo
 * return to web.
 * Third how to check this token is lawful. first we get redis version from redis,if is not null,
 * we think this redis is fine. and get token from redis and check is equal. if is, return true.
 * if the redis version is null, we think redis service is down and go to auth to check this token
 * is lawful.
 */
@Component
public class AuthFilter extends ZuulFilter {

    @Autowired
    private AuthFegin authFegin;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        try {
            HttpServletRequest request = requestContext.getRequest();
            String uri = request.getRequestURI();
            if (request.getMethod().equals("OPTIONS")) {
                return null;
            }
            System.out.println("=========================>" + uri);
            Boolean isNoAuthenticationUrl = uri.endsWith(URLConstant.NO_AUTH_COMMENT);
            Object token = request.getHeader(JWTIUtil.TOKEN);
            if (isNoAuthenticationUrl && token == null) {
                setUnauthorizedResponse(requestContext,Result.ofFail(BaseExceptionCode.B_NOT_INVALID.getCode(),BaseExceptionCode.B_PARAM_FAIL.getMessage()));
            }
            if (isNoAuthenticationUrl && token != null) {

                Result result = authFegin.checkTokenIsLawful((String) token);
                if (!result.getCode().equals(Result.OK.getCode())) {
                    setUnauthorizedResponse(requestContext,result);
                }
            }
        } catch (Exception e) {
            setUnauthorizedResponse(requestContext,Result.ofFail(BaseExceptionCode.B_NOT_INVALID.getCode(),BaseExceptionCode.B_PARAM_FAIL.getMessage()));
        }

        return null;
    }

    private void setUnauthorizedResponse(RequestContext requestContext, Result result) throws JsonProcessingException {
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.OK.value());

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(result);

        requestContext.setResponseBody(body);
        requestContext.getResponse().setContentType("application/json; charset=UTF-8");
    }


}
