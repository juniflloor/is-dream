package is.dream.common.utils;

import java.util.Random;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/30 5:44
 */
public class CodeIUtil {

    public static String getCode(){
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int r = random.nextInt(10);
            code = code + r;
        }
        return code;
    }
}
