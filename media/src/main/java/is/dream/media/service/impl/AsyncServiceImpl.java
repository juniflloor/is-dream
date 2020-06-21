package is.dream.media.service.impl;

import is.dream.dao.base.service.VideoService;
import is.dream.dao.entiry.Video;
import is.dream.media.exception.MediaBusinessException;
import is.dream.media.handler.ffmpeg.untils.MediaUtil;
import is.dream.media.handler.ffmpeg.untils.SystemUtils;
import is.dream.media.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/18 18:16
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private VideoService videoService;

    @Async("myTaskAsyncPool")
    @Override
    public void convertM3u8(File sourceFile, File videoFile,File imageFile, String fileName,String videoId ) throws MediaBusinessException {

        MediaUtil.convertM3u8(sourceFile, videoFile, imageFile, fileName);
        Video video = videoService.getVideoById(videoId);
        if (ObjectUtils.isEmpty(video)) {

            SystemUtils.deleteLocalFiles(sourceFile);
            SystemUtils.deleteLocalFiles(videoFile);
        }
    }
}
