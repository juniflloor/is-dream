package is.dream.common.exception;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/15 1:37
 */
public enum BusinessExceptionCode {

    PARAM_FAIL ("1","参数错误"),

    USER_ERROR ("2","用户命或者密码错误"),

    ERROR_TOKEN ("3","令牌错误");

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    BusinessExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
