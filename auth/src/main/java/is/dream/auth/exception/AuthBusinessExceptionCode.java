package is.dream.auth.exception;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 4:23
 */
public enum AuthBusinessExceptionCode {

    USER_ERROR ("2","用户命或者密码错误"),

    ERROR_TOKEN ("3","令牌無效"),;

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    AuthBusinessExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
