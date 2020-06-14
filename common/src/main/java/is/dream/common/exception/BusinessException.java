package is.dream.common.exception;

import is.dream.common.Result;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/15 1:34
 */
public class BusinessException extends Exception{


    private String code;

    public String getCode() {
        return code;
    }

    /**
     * @param code 值
     * @param message  详情
     */
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(BusinessExceptionCode businessExceptionCode) {
        super(businessExceptionCode.getMessage());
        this.code = businessExceptionCode.getCode();
    }

}
