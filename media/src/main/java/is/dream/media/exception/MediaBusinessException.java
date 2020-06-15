package is.dream.media.exception;

import is.dream.common.exception.BaseBusinessException;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 4:45
 */
public class MediaBusinessException extends BaseBusinessException {

    public MediaBusinessException(String code, String message) {
        super(code, message);
    }

    public MediaBusinessException(MediaBusinessExceptionCode mediaBusinessExceptionCode) {

        super(mediaBusinessExceptionCode.getCode(),mediaBusinessExceptionCode.getMessage());
    }
}
