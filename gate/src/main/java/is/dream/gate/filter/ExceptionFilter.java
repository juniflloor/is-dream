package is.dream.gate.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/15 4:23
 */
@Component
public class ExceptionFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().containsKey("error.status_code");
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Object e = ctx.get("error.exception");
            if (e!= null && e instanceof ZuulException) {
                ctx.remove("error.status_code");
                ctx.setResponseBody("Overriding Zuul Exception Body");
                ctx.getResponse().setContentType("application/json");
                ctx.setResponseStatusCode(500);
            }
        } catch (Exception ex) {
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;

    }
}
