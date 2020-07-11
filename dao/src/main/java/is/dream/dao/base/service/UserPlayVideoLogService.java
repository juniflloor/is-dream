package is.dream.dao.base.service;

import is.dream.dao.entiry.UserPlayVideoLog;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 11:51
 */
public interface UserPlayVideoLogService {

    void save(UserPlayVideoLog UserPlayVideoLog);

    List<UserPlayVideoLog> getByAssociatedUserId(String associatedUserId);
}
