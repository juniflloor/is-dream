package is.dream.media.service;

import is.dream.media.exception.MediaBusinessException;

import java.io.File;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/18 18:17
 */
public interface AsyncService {

    void convertM3u8(File sourceFile, File videoFile,File imageFile, String fileName,String videoId) throws MediaBusinessException;
}
