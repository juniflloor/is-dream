package is.dream.common.utils;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/30 4:39
 */
public class RegexIUtil {

    public static final String PW_PATTERN = "^[a-z0-9A-Z]+$";;

    public static boolean isLawful(String matchString){

        if (matchString.trim().length() <= 8) {
            return false;
        }
        return matchString.matches(PW_PATTERN);
    }
}
