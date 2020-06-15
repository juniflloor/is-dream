package is.dream.auth.service.impl;

import is.dream.auth.exception.AuthBusinessException;
import is.dream.auth.exception.AuthBusinessExceptionCode;
import is.dream.auth.service.UserBusinessService;
import is.dream.cache.constants.CacheConstants;
import is.dream.cache.utils.RedisUtils;
import is.dream.common.Result;
import is.dream.common.exception.BaseBusinessException;
import is.dream.common.exception.BaseExceptionCode;
import is.dream.common.utils.JWTUtil;
import is.dream.dao.base.service.UserService;
import is.dream.dao.entiry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/10 1:01
 */
@Service
public class UserBusinessServiceImpl implements UserBusinessService {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Result<Object> login(String userName, String password) throws BaseBusinessException {

        Result<Object> result = Result.OK;
        if (ObjectUtils.isEmpty(userName) || ObjectUtils.isEmpty(password)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        User dbUser = userService.getByUserNameAndPassword(userName, password);
        if (ObjectUtils.isEmpty(dbUser)) {
            throw new AuthBusinessException(AuthBusinessExceptionCode.USER_ERROR);
        }

        String token = JWTUtil.createToken(dbUser, CacheConstants.USER_TOKEN_EXPIRE);
        userService.updateUserToken(dbUser.getUserId(), token.substring(0,50));
        redisUtils.set(CacheConstants.USER_TOKEN_REDIS_PREFIX + dbUser.getUserId(),token, CacheConstants.USER_TOKEN_EXPIRE);
        result.setData(dbUser);
        return result;
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
    public Result<Object> isLogin(String token) { return null; }

    @Override
    public Result<Object> isLawful(String token) throws BaseBusinessException {

        Result result = Result.OK;
        User user = JWTUtil.deciphering(token,User.class);
        if (ObjectUtils.isEmpty(user)) {
            throw new AuthBusinessException(AuthBusinessExceptionCode.ERROR_TOKEN);
        }
        result.setData(user);
        Object redisVersion = redisUtils.get(CacheConstants.REDIS_START_VERSION);
        if (!ObjectUtils.isEmpty(redisVersion)) {
            String cacheToken =  (String) redisUtils.get(CacheConstants.USER_TOKEN_REDIS_PREFIX + user.getUserId());
            if (!token.equals(cacheToken)) {
                throw new AuthBusinessException(AuthBusinessExceptionCode.ERROR_TOKEN);
            }
            return  result;
        }

        User dbUser = userService.getByUserId(user.getUserId());
        if (!(token.substring(0,50)).equals(dbUser.getToken())) {
            throw new AuthBusinessException(AuthBusinessExceptionCode.ERROR_TOKEN);
        }

        return result;
    }
}
