package is.dream.common.utils;

import java.text.DecimalFormat;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/27 15:59
 */
public class TimeUtils {

    private static final int BASE_TIME_MILLISECONDS = 1000;
    private static final int BASE_TIME_SECONDS = 60;
    private static final int BASE_TIME_HOUR = 24;

    public static String formatDateTime(long milliseconds) {
        StringBuilder sb = new StringBuilder();
        long mss = milliseconds / BASE_TIME_MILLISECONDS;
        long days = mss / (BASE_TIME_SECONDS * BASE_TIME_SECONDS * BASE_TIME_HOUR);
        long hours = (mss % (BASE_TIME_SECONDS * BASE_TIME_SECONDS * BASE_TIME_HOUR)) / (BASE_TIME_SECONDS * BASE_TIME_SECONDS);
        long minutes = (mss % (BASE_TIME_SECONDS * BASE_TIME_SECONDS)) / BASE_TIME_SECONDS;
        long seconds = mss % BASE_TIME_SECONDS;
        DecimalFormat format = new DecimalFormat("00");
        if (days > 0 || hours > 0) {
            sb.append(format.format(hours)).append(":").append(format.format(minutes)).append(":").append(format.format(seconds));
        }else {
            sb.append(format.format(minutes)).append(":").append(format.format(seconds));
        }
        return sb.toString();
    }

}
