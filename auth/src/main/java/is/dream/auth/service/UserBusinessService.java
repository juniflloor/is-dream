package is.dream.auth.service;

import is.dream.dao.entiry.User;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/10 1:00
 */
public interface UserBusinessService {

     User login();

     User register();

     User logout();

     Boolean isLogin();

}
