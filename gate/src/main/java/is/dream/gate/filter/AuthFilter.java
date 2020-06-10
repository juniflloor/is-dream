package is.dream.gate.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import is.dream.common.utils.JWTUtil;
import is.dream.gate.contants.URLConstant;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

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
public class AuthFilter extends ZuulFilter {

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
        HttpServletRequest request = requestContext.getRequest();
        Object token = request.getHeader(JWTUtil.TOKEN);
        String uri = request.getRequestURI();
        Boolean tokenIsLawful = checkTokenIsLawful(token);

        if (uri.endsWith(URLConstant.NO_AUTH_LOGIN) || uri.endsWith(URLConstant.NO_AUTH_REGISTER)) {
            return  null;
        }

        return null;
    }

    private boolean checkTokenIsLawful (Object token) {

        return true;
    }
}
