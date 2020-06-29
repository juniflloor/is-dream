package is.dream.common.utils;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/30 4:39
 */
public class RegexUtil {

    public static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";

    public static boolean isLawful(String matchString){
        return matchString.matches(PW_PATTERN);
    }
}
