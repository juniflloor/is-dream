package is.dream.common.exception;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/15 1:34
 */
public class BaseBusinessException extends RuntimeException{


    private String code;

    public String getCode() {
        return code;
    }

    /**
     * @param code 值
     * @param message  详情
     */
    public BaseBusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseBusinessException(BaseExceptionCode baseExceptionCode) {
        super(baseExceptionCode.getMessage());
        this.code = baseExceptionCode.getCode();
    }

}
