package is.dream.gate.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import is.dream.common.Result;
import is.dream.common.exception.BusinessException;
import is.dream.common.exception.BusinessExceptionCode;
import is.dream.common.utils.JWTUtil;
import is.dream.gate.contants.URLConstant;
import is.dream.gate.fegin.AuthFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

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
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        try {
            HttpServletRequest request = requestContext.getRequest();
            Object token = request.getHeader(JWTUtil.TOKEN);
            String uri = request.getRequestURI();
            System.out.println("=========================>" + uri);
            Boolean isNoAuthenticationUrl = uri.endsWith(URLConstant.NO_AUTH_LOGIN) || uri.endsWith(URLConstant.NO_AUTH_REGISTER);
            if (ObjectUtils.isEmpty(token) && isNoAuthenticationUrl) {
                return null;
            }
            Boolean tokenIsLawful = true;
            Result result = authFegin.checkTokenIsLawful((String) token);
            if (!result.getCode().equals(Result.OK.getCode())) {
                tokenIsLawful = false;
            }

            if (!tokenIsLawful) {
                // 抛出异常
                throw new BusinessException(BusinessExceptionCode.ERROR_TOKEN);
            }
        } catch (Exception e) {
            requestContext.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            requestContext.set("error.exception", e);
        }

        return null;
    }

}
