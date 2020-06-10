package is.dream.common;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 16:36
 * A uniform entity format for the request response
 */
@Data
@AllArgsConstructor
public class Result<T>{

    private String code;

    private String message;

    private T data;

    public static final Result OK = new Result<String>("0","SUCCESS","成功");

    public static final Result PARAM_FAIL = new Result<String>("1","参数错误","");

    public static final Result USER_ERROR = new Result<String>("2","用户命或者密码错误","");

    public static final Result ERROR_TOKEN = new Result<String>("3","令牌错误","");
}
