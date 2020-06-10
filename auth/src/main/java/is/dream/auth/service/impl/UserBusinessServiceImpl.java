package is.dream.auth.service.impl;

import is.dream.auth.service.UserBusinessService;
import is.dream.cache.constants.AuthCacheConstants;
import is.dream.cache.utils.RedisUtils;
import is.dream.common.Result;
import is.dream.common.utils.JWTUtil;
import is.dream.dao.base.service.UserService;
import is.dream.dao.entiry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/10 1:01
 */
public class UserBusinessServiceImpl implements UserBusinessService {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Result<Object> login(User user) {

        if (ObjectUtils.isEmpty(user)) {
            return Result.PARAM_FAIL;
        }
        User dbUser = userService.getByUserNameAndPassword(user.getUserName(), user.getPassword());
        if (ObjectUtils.isEmpty(dbUser)) {
            return Result.USER_ERROR;
        }

        String token = JWTUtil.createToken(dbUser, AuthCacheConstants.USER_TOKEN_EXPIRE);
        userService.updateUserToken(dbUser.getUserId(),token);
        redisUtils.set(AuthCacheConstants.USER_TOKEN_REDIS_PREFIX + dbUser.getUserId(),token,AuthCacheConstants.USER_TOKEN_EXPIRE);

        return Result.OK;
    }

    @Override
    public Result<Object> register(User user) {
        return null;
    }

    @Override
    public Result<Object> logout(User user) {
        return null;
    }

    @Override
    public Result<Object> isLogin(String token) {
        return null;
    }
}
