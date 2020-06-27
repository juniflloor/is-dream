package is.dream.dao.base.service;

import is.dream.dao.entiry.User;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 19:14
 */
public interface UserService {

    User getByUserNameAndPassword(String userName, String password);

    void updateUserToken(String userId,String token);

    User getByUserId(String userId);

    List<User> getByIdIn(List<String> idList);
}
