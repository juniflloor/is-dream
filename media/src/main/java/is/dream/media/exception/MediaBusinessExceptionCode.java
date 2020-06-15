package is.dream.media.exception;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 5:00
 */
public enum MediaBusinessExceptionCode {

    VIDEO_IS_NULL ("M_100000","上傳視頻為空"),

    VIDEO_FORMAT_FAIL ("M_100001","文件格式不正确"),

    VIDEO_UPLOAD_FAIL ("M_100002","文件上傳失敗"),

    ;

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    MediaBusinessExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
