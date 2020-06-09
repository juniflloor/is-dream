package is.dream.dao.base.service;

import is.dream.dao.entiry.User;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 19:14
 */
public interface UserService {
    User getByUserNameAndPassword(String userName, String password);
}
