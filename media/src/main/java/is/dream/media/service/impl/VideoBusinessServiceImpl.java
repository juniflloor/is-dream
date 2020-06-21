package is.dream.media.service.impl;

import is.dream.common.Result;
import is.dream.dao.base.service.VideoService;
import is.dream.dao.entiry.ImageUiSetting;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
    public Result<Object> upload(MultipartFile file, String title, String introduction, String startTime, int width, int high, boolean isGenerateUiImage, ImageUiSetting imageUiSetting) throws MediaBusinessException {

        if (ObjectUtils.isEmpty(file)) {
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_FILE_IS_NULL);
        }

        if (StringUtils.isEmpty(title)) {
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_TITLE_IS_NULL);
        }

        if (isGenerateUiImage && StringUtils.isEmpty(imageUiSetting)) {
            throw new MediaBusinessException(MediaBusinessExceptionCode.IMAGE_UI_SETTING_NOT_FOUND);
        }

        Video video = new Video();
        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        File sourceFile = null, videoFile = null, imageDefaultFile = null, imageUIFile = null;
        try{
            sourceFile = new File(videoConfig.getSourcePath() + originalFilename );
            file.transferTo(sourceFile);
        } catch (Exception e) {
            if (!ObjectUtils.isEmpty(sourceFile)) {
                SystemUtils.deleteLocalFiles(sourceFile);
            }
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_SAVE_SOURCE_FAIL);
        }

        if (isGenerateUiImage) {

            try {
                imageUIFile = new File(videoConfig.getImageUIPath() + imageUiSetting.getImageLocation());
                if (!imageUIFile.exists()) {
                    imageUIFile.mkdirs();
                }
                MediaUtil.cutVideoFrame(sourceFile, imageDefaultFile, startTime, fileName, imageUiSetting.getWidth(), imageUiSetting.getHigh(), false);
            } catch (Exception e) {
                if (!ObjectUtils.isEmpty(imageUIFile)) {
                    SystemUtils.deleteLocalFiles(imageUIFile);
                }
                throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_SAVE_SOURCE_FAIL);
            }
        }

        VideoMetaInfo videoMetaInfo = null;
        try {
            imageDefaultFile = new File(videoConfig.getImageDefaultPath() + fileName );
            if (!imageDefaultFile.exists()) {
                imageDefaultFile.mkdirs();
            }
            videoMetaInfo = MediaUtil.cutVideoFrame(sourceFile,imageDefaultFile,startTime,fileName,width,high,true);

        } catch (Exception e) {
            if (!ObjectUtils.isEmpty(imageDefaultFile)) {
                SystemUtils.deleteLocalFiles(imageDefaultFile);
            }
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_CUT_IMAGE_FAIL);
        }

        try {
            videoFile = new File(videoConfig.getTargetPath() + fileName );
            if (!videoFile.exists()) {
                videoFile.mkdirs();
            }
            asyncService.convertM3u8(sourceFile, videoFile,imageDefaultFile, fileName);
            video.setDefault();
            video.setId(UUID.randomUUID().toString());
            video.setTitle(title);
            video.setName(originalFilename);
            video.setTag("1");
            video.setYear("2020");
            video.setSuffix("mp4");
            video.setType("1");
            video.setSourceLocation(videoConfig.getSourcePath() + originalFilename);
            video.setDuration(videoMetaInfo.getDuration());
            video.setIntroduction(introduction);
            video.setCoverImageUrl(videoConfig.getImageDefaultUrl() + fileName + "/" +fileName + ".jpg");
            String playUrl = videoConfig.getAccessUrl() + fileName + "/" +fileName + ".m3u8";
            video.setPlayUrl(playUrl);
            Date currentDate = new Date(System.currentTimeMillis());
            video.setCreateTime(currentDate);
            video.setUpdateTime(currentDate);
            videoService.saveFull(video);
        } catch (Exception e) {
            SystemUtils.deleteLocalFiles(sourceFile);
            SystemUtils.deleteLocalFiles(videoFile);
            SystemUtils.deleteLocalFiles(imageDefaultFile);
            SystemUtils.deleteLocalFiles(imageUIFile);
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_TRANS_TARGET_FAIL);
        }
        return Result.OK;
    }

    @Override
    public Result<Object> getSift() {

        List<Video> data = new ArrayList<>();
        data.addAll(videoService.getNewest());
        data.add(videoService.getHottest());
        data.add(videoService.getHighestScore());
        return Result.setSpecialData(data);
    }

    @Override
    public Result<Object> getVideoById(String id) {
        return Result.setSpecialData(videoService.getVideoById(id));
    }

}
