package is.dream.auth.exception;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 4:23
 */
public enum AuthBusinessExceptionCode {

    USER_ERROR ("A_100000","用户命或者密码错误"),

    ERROR_TOKEN ("A_100001","令牌無效"),

    USERNAME_IS_EXIST ("A_100002","用户名已存在"),

    EMAIL_IS_EXIST ("A_100003","该邮箱已注册"),

    PASSWORD_IS_NOT_LAWFUL ("A_100004","密码不合法"),

    EMAIL_SEND_FAIL ("A_100005","邮件发送失败"),

    EMAIL_CODE_ERROR ("A_100006","验证码错误"),

    ;

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
