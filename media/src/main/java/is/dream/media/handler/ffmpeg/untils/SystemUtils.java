package is.dream.media.handler.ffmpeg.untils;

import java.io.File;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 15:36
 */
public class SystemUtils {

    public static void deleteLocalFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                f.delete();
            }
        }
        file.delete();
    }
}
