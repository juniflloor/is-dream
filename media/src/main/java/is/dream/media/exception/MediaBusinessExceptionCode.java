package is.dream.media.exception;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 5:00
 */
public enum MediaBusinessExceptionCode {

    VIDEO_IS_NULL ("M_100000","上傳視頻為空"),

    VIDEO_FORMAT_FAIL ("M_100001","文件格式不正确"),

    VIDEO_FILE_IS_NULL("M_100002","视频文件为空"),

    VIDEO_TITLE_IS_NULL ("M_100003","视频标题为空"),

    VIDEO_SAVE_SOURCE_FAIL ("M_100004","保存原视频失败"),

    VIDEO_TRANS_TARGET_FAIL ("M_100005","视频转换失败"),

    VIDEO_CUT_IMAGE_FAIL ("M_100006","视频截取图片失败"),

    VIDEO_IS_EXIST ("M_100006","源文件存在"),

    IMAGE_UI_SETTING_NOT_FOUND ("M_200001","前端图片设置没找到"),

    IMAGE_UI_GENERATE_FAUL ("M_200002","生成前端图片失败"),

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
