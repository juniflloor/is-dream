package is.dream.common;


import is.dream.common.exception.BaseExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 16:36
 * A uniform entity format for the request response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T>{

    private String code;

    private String message;

    private T data;

    public static Result ofFail(BaseExceptionCode resultEnum) {
        Result result = new Result();
        result.code = resultEnum.getCode();
        result.message = resultEnum.getMessage();
        return result;
    }

    public static Result ofFail(String code, String message) {
        Result result = new Result();
        result.code = code;
        result.message = message;
        return result;
    }

    public static final Result OK = new Result<String>("0","SUCCESS","成功");
}
