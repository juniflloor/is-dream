package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.UserService;
import is.dream.dao.entiry.User;
import is.dream.dao.inter.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 19:05
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getByUserNameAndPassword(String userName, String password){
        return userDao.getByUserNameAndPassword(userName,password);
    }

    @Override
    public void updateUserToken(String userId, String token) {

        userDao.updateUserToken(userId,token);
    }

    @Override
    public User getByUserId(String userId) {

        return userDao.getByUserId(userId);
    }

    @Override
    public List<User> getByIdIn(List<String> idList) {
        return userDao.getByIdIn(idList);
    }

}
