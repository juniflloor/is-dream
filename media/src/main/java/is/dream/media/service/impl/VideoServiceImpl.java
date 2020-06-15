package is.dream.media.service.impl;

import is.dream.common.Result;
import is.dream.dao.entiry.Video;
import is.dream.media.config.VideoConfig;
import is.dream.media.exception.MediaBusinessException;
import is.dream.media.exception.MediaBusinessExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 4:00
 */
@Service
public class VideoServiceImpl {

    @Autowired
    private VideoConfig videoConfig;

    public Result<Object> upload(MultipartFile file, String title) throws MediaBusinessException {

        if (file == null || file.isEmpty()) {
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_IS_NULL);
        }

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        Video video = new Video();
        video.setId(UUID.randomUUID().toString());
        video.setTitle(title);
        video.setName(originalFilename.substring(0, originalFilename.lastIndexOf(".")));
        video.setSuffix(suffix);

        if(true){
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_FORMAT_FAIL);
        }


        File upload = new File(videoConfig.getLocalPath());
        if (!upload.exists()) {
            upload.mkdirs();
        }
        try {
            file.transferTo(new File(videoConfig.getLocalPath() + video.getId() + video.getSuffix()));
            video.setUploadTime(new Date());
//            videoService.insert(video);
        } catch (Exception e) {
            // 删除不完整上传视频
//            SystemUtils.deleteLocalFiles(new File(videoConfig.getLocalPath() + video.getvId() + video.getvSuffix()));
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_UPLOAD_FAIL);
        }

        return null;

    }
}
