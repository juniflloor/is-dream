package is.dream.cache.constants;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/11 1:22
 */
public class AuthCacheConstants {

    /**
     * User Cache Constant
     */
    public static final long USER_TOKEN_EXPIRE = 24 * 60 * 60 * 1000l;

    public static final String USER_TOKEN_REDIS_PREFIX = "IS_DREAM_USER_TOKEN_";

    /**
     * Redis Constant
     */
    public static final String REDIS_START_VERSION = "REDIS_VERSION";
}
