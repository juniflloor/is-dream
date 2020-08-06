package is.dream.media.service;

import is.dream.common.Result;
import is.dream.dao.entiry.UserPlayVideoLog;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 12:27
 */
public interface UserPlayVideoLogBusinessService {

    Result<Object> save(UserPlayVideoLog userPlayVideoLog);

    Result<Object> getByAssociatedUserId(String associatedUserId);
}
