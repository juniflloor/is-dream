package is.dream.common.exception;

import com.netflix.client.ClientException;
import com.netflix.zuul.exception.ZuulException;
import feign.FeignException;
import is.dream.common.Result;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/15 0:33
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    MessageSource messageSource;

    /**
     * NoHandlerFoundException 404 异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handlerNoHandlerFoundException(NoHandlerFoundException e) throws Throwable {

        return Result.ofFail(CommonExceptionCode.NOT_FOUND);
    }

    /**
     * HttpRequestMethodNotSupportedException 405 异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) throws Throwable {
        return Result.ofFail(CommonExceptionCode.METHOD_NOT_ALLOWED);
    }

    /**
     * HttpMediaTypeNotSupportedException 415 异常处理
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result handlerHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) throws Throwable {
        return Result.ofFail(CommonExceptionCode.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Exception 类捕获 500 异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public Result handlerException(Exception e) throws Throwable {
        return ifDepthExceptionType(e);
    }

    /**
     * 深度错误类型
     */
    private Result ifDepthExceptionType(Throwable throwable) throws Throwable {
        Throwable cause = throwable.getCause();
        if (cause instanceof ClientException) {
            return handlerClientException((ClientException) cause);
        }
        if (cause instanceof FeignException) {
            return handlerFeignException((FeignException) cause);
        }
        return Result.ofFail(CommonExceptionCode.EXCEPTION);
    }

    /**
     * FeignException 类捕获
     */
    @ExceptionHandler(value = FeignException.class)
    public Result handlerFeignException(FeignException e) throws Throwable {
        return Result.ofFail(CommonExceptionCode.RPC_ERROR);
    }

    /**
     * ClientException 类捕获
     */
    @ExceptionHandler(value = ClientException.class)
    public Result handlerClientException(ClientException e) throws Throwable {
        return Result.ofFail(CommonExceptionCode.RPC_ERROR);
    }

    /**
     * ZuulException 类捕获
     */
    @ExceptionHandler(value = ZuulException.class)
    public Result handlerZuulException(ZuulException e) throws Throwable {
        return Result.ofFail(CommonExceptionCode.ZUUL_ERROR);
    }

    /**
     * BusinessException 类捕获
     */
    @ExceptionHandler(value = BusinessException.class)
    public Result handlerBusinessException(BusinessException e) throws Throwable {
        return Result.ofFail(e.getCode(), e.getMessage());
    }

}
