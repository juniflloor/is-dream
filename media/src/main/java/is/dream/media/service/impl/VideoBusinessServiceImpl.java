package is.dream.media.service.impl;

import is.dream.common.Result;
import is.dream.dao.base.service.VideoService;
import is.dream.dao.entiry.Video;
import is.dream.media.config.VideoConfig;
import is.dream.media.exception.MediaBusinessException;
import is.dream.media.exception.MediaBusinessExceptionCode;
import is.dream.media.handler.ffmpeg.entity.VideoMetaInfo;
import is.dream.media.handler.ffmpeg.untils.MediaUtil;
import is.dream.media.handler.ffmpeg.untils.SystemUtils;
import is.dream.media.service.AsyncService;
import is.dream.media.service.VideoBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 4:00
 */
@Service
public class VideoBusinessServiceImpl implements VideoBusinessService {

    @Autowired
    private VideoConfig videoConfig;

    @Autowired
    private VideoService videoService;

    @Autowired
    private AsyncService asyncService;

    @Override
    public Result<Object> upload(MultipartFile file, String title,String introduction,String startTime) throws MediaBusinessException {

        if (ObjectUtils.isEmpty(file)) {
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_FILE_IS_NULL);
        }

        if (StringUtils.isEmpty(title)) {
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_TITLE_IS_NULL);
        }

        Video video = new Video();
        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        File sourceFile = null, targetFile = null, imageFile = null;
        try{
            sourceFile = new File(videoConfig.getSourcePath() + originalFilename );
            file.transferTo(sourceFile);
        } catch (Exception e) {
            if (!ObjectUtils.isEmpty(sourceFile)) {
                SystemUtils.deleteLocalFiles(sourceFile);
            }
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_SAVE_SOURCE_FAIL);
        }

        VideoMetaInfo videoMetaInfo = null;
        try {
            imageFile = new File(videoConfig.getCoverImagePath() + fileName );
            if (!imageFile.exists()) {
                imageFile.mkdirs();
            }
            videoMetaInfo = MediaUtil.cutVideoFrame(sourceFile,imageFile,startTime,fileName);
        } catch (Exception e) {
            if (!ObjectUtils.isEmpty(imageFile)) {
                SystemUtils.deleteLocalFiles(imageFile);
            }
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_CUT_IMAGE_FAIL);
        }

        try {
            targetFile = new File(videoConfig.getTargetPath() + fileName );
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            asyncService.convertM3u8(sourceFile, targetFile, fileName);
            video.setDefault();
            video.setId(UUID.randomUUID().toString());
            video.setTitle(title);
            video.setName(originalFilename);
            video.setYear("2020");
            video.setSuffix("mp4");
            video.setType("1");
            video.setDuration(videoMetaInfo.getDuration());
            video.setIntroduction(introduction);
            video.setCoverImageUrl(videoConfig.getAccessUrl() + "image/" + fileName + ".jpg");
            String playUrl = videoConfig.getAccessUrl() + fileName + ".m3u8";
            video.setPlayUrl(playUrl);
            Timestamp timestamp = new Timestamp(new Date().getTime());
            video.setCreateTime(timestamp);
            video.setUpdateTime(timestamp);
            videoService.saveFull(video);
        } catch (Exception e) {
            SystemUtils.deleteLocalFiles(sourceFile);
            SystemUtils.deleteLocalFiles(targetFile);
            if (!ObjectUtils.isEmpty(imageFile)) {
                SystemUtils.deleteLocalFiles(imageFile);
            }
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_TRANS_TARGET_FAIL);
        }
        return Result.OK;
    }


}