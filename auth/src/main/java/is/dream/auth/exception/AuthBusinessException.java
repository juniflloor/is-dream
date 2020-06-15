package is.dream.auth.exception;

import is.dream.common.exception.BaseBusinessException;
/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 4:22
 */
public class AuthBusinessException extends BaseBusinessException {

    public AuthBusinessException(String code, String message) {
        super(code, message);
    }

    public AuthBusinessException(AuthBusinessExceptionCode authBusinessExceptionCode) {

        super(authBusinessExceptionCode.getCode(),authBusinessExceptionCode.getMessage());
    }
}
