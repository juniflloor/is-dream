package is.dream.gate.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import is.dream.gate.contants.URLConstant;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 21:57
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
        Object token = request.getHeader("token");
        String uri = request.getRequestURI();
        if (uri.endsWith(URLConstant.NO_AUTH_LOGIN) || uri.endsWith(URLConstant.NO_AUTH_REGISTRATION)) {
            return  null;
        }
        if (ObjectUtils.isEmpty(token)) {
            // TODO skip login url
            return null;
        }

        // TODO check token


        return null;
    }
}
