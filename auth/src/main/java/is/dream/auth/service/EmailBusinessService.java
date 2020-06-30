package is.dream.auth.service;

import is.dream.common.Result;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/30 5:41
 */
public interface EmailBusinessService {

    Result<Object> sendSimpleMail(String to);

}
