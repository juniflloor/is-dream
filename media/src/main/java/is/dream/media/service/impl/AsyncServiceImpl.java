package is.dream.media.service.impl;

import is.dream.media.exception.MediaBusinessException;
import is.dream.media.handler.ffmpeg.untils.MediaUtil;
import is.dream.media.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/18 18:16
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    @Async("myTaskAsyncPool")
    @Override
    public void convertM3u8(File sourceFile, File targetFile, String fileName) throws MediaBusinessException {

        MediaUtil.convertM3u8(sourceFile, targetFile, fileName);
    }
}
