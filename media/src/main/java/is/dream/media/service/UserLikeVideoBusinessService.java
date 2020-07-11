package is.dream.media.service;

import is.dream.common.Result;
import is.dream.dao.entiry.UserLikeVideo;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 15:31
 */
public interface UserLikeVideoBusinessService {

    Result<Object> save(UserLikeVideo userLikeVideo);

    Result<Object> getByAssociatedUserId(String associatedUserId);
}
