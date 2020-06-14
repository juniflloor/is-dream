package is.dream.auth.service;

import is.dream.common.Result;
import is.dream.common.exception.BusinessException;
import is.dream.dao.entiry.User;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/10 1:00
 */
public interface UserBusinessService {

     Result<Object> login(String userName, String password) throws BusinessException;

     Result<Object> register(User user);

     Result<Object> logout(User user);

     Result<Object> isLogin(String token);

     Result<Object> isLawful(String token) throws BusinessException;

}
