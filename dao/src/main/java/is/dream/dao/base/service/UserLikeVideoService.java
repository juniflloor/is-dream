package is.dream.dao.base.service;

import is.dream.dao.entiry.UserLikeVideo;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 15:24
 */
public interface UserLikeVideoService {

    void save(UserLikeVideo userLikeVideo);

    List<UserLikeVideo> getByAssociatedUserId(String associatedUserId);
}
