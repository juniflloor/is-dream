package is.dream.media.service.impl;

import is.dream.common.Result;
import is.dream.common.constants.DBConstant;
import is.dream.common.exception.BaseBusinessException;
import is.dream.common.exception.BaseExceptionCode;
import is.dream.dao.base.service.ImageUiService;
import is.dream.dao.base.service.ImageUiSettingService;
import is.dream.dao.base.service.VideoOperationService;
import is.dream.dao.base.service.VideoService;
import is.dream.dao.entiry.ImageUi;
import is.dream.dao.entiry.ImageUiSetting;
import is.dream.dao.entiry.Video;
import is.dream.dao.entiry.VideoOperation;
import is.dream.media.config.VideoConfig;
import is.dream.media.dto.VideoDto;
import is.dream.media.exception.MediaBusinessException;
import is.dream.media.exception.MediaBusinessExceptionCode;
import is.dream.media.handler.ffmpeg.entity.VideoMetaInfo;
import is.dream.media.handler.ffmpeg.untils.MediaUtil;
import is.dream.media.handler.ffmpeg.untils.SystemUtils;
import is.dream.media.service.AsyncService;
import is.dream.media.service.VideoBusinessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Date;
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

    @Autowired
    private ImageUiService imageUiService;

    @Autowired
    private ImageUiSettingService imageUiSettingService;

    @Autowired
    private VideoOperationService videoOperationService;

    @Override
    @Transactional
    public Result<Object> upload(MultipartFile file,String name,String type,String tag,String title,String subtitle,String year,
                                 String introduction, String startTime,boolean isGenerateUiImage, ImageUiSetting imageUiSetting) throws MediaBusinessException {

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
        String videoId = UUID.randomUUID().toString();
        String realFileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        File sourceFile = null, videoFile = null, imageDefaultFile = null, imageUIFile = null;
        try{
            sourceFile = new File(videoConfig.getSourcePath() + originalFilename );
            if (sourceFile.exists()) {
                throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_IS_EXIST);
            }
            file.transferTo(sourceFile);
        } catch (Exception e) {
            if (!ObjectUtils.isEmpty(sourceFile)) {
                SystemUtils.deleteLocalFiles(sourceFile);
            }
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_SAVE_SOURCE_FAIL);
        }

        ImageUi imageui = null;
        if (isGenerateUiImage) {

            try {
                imageUIFile = new File(videoConfig.getImageUIPath() + imageUiSetting.getImageLocation());
                if (!imageUIFile.exists()) {
                    imageUIFile.mkdirs();
                }
                MediaUtil.cutVideoFrame(sourceFile, imageUIFile, startTime, videoId, imageUiSetting.getWidth(), imageUiSetting.getHigh(), false);
            } catch (Exception e) {
                if (!ObjectUtils.isEmpty(imageUIFile)) {
                    SystemUtils.deleteLocalFiles(imageUIFile);
                }
                throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_SAVE_SOURCE_FAIL);
            }
            imageui = new ImageUi();
            imageui.setId(UUID.randomUUID().toString());
            imageui.setImageUrl(videoConfig.getImageUIUrl() + imageUiSetting.getImageLocation() + "/" + videoId + ".jpg");
            imageui.setAssociatedImageUiSettingId(imageUiSetting.getId());
            imageui.setCreateTime(new Date(System.currentTimeMillis()));
            imageui.setUpdateTime(new Date(System.currentTimeMillis()));
        }

        VideoMetaInfo videoMetaInfo = null;
        try {
            imageDefaultFile = new File(videoConfig.getImageDefaultPath() + videoId );
            if (!imageDefaultFile.exists()) {
                imageDefaultFile.mkdirs();
            }
            ImageUiSetting defaultImageUiSetting = imageUiSettingService.getByImageLocation("default_all");
            videoMetaInfo = MediaUtil.cutVideoFrame(sourceFile,imageDefaultFile,startTime,videoId,defaultImageUiSetting.getWidth(),
                                                    defaultImageUiSetting.getHigh(),true);

        } catch (Exception e) {
            if (!ObjectUtils.isEmpty(imageDefaultFile)) {
                SystemUtils.deleteLocalFiles(imageDefaultFile);
            }
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_CUT_IMAGE_FAIL);
        }

        try {
            videoFile = new File(videoConfig.getTargetPath() + videoId );
            if (!videoFile.exists()) {
                videoFile.mkdirs();
            }
            asyncService.convertM3u8(sourceFile, videoFile,imageDefaultFile, videoId,videoId);
            video.setDefault();
            video.setId(videoId);
            video.setTitle(title);
            video.setName(name);
            video.setFileName(realFileName);
            video.setTag(tag);
            video.setYear(year);
            video.setSuffix(originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase());
            video.setType(type);
            video.setSubtitle(subtitle);
            video.setSourceLocation(videoConfig.getSourcePath() + originalFilename);
            video.setDuration(videoMetaInfo.getDuration());
            video.setIntroduction(introduction);
            video.setDefaultImageUrl(videoConfig.getImageDefaultUrl() + videoId + "/" +videoId + ".jpg");
            String playUrl = videoConfig.getAccessUrl() + videoId + "/" +videoId + ".m3u8";
            video.setPlayUrl(playUrl);
            Date currentDate = new Date(System.currentTimeMillis());
            video.setCreateTime(currentDate);
            video.setUpdateTime(currentDate);
            videoService.saveFull(video);
            imageui.setAssociatedVideoId(video.getId());
            imageUiService.save(imageui);
        } catch (Exception e) {
            SystemUtils.deleteLocalFiles(sourceFile);
            SystemUtils.deleteLocalFiles(videoFile);
            SystemUtils.deleteLocalFiles(imageDefaultFile);
            File thisImageUiFile = new File(videoConfig.getImageUIPath() + imageUiSetting.getImageLocation() + "/" + videoId + ".jpg" );
            if (thisImageUiFile.exists()) {
                SystemUtils.deleteLocalFiles(thisImageUiFile);
            }
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_TRANS_TARGET_FAIL);
        }
        return Result.OK;
    }

    @Override
    public Result<Object> getVideoById(String id) {

        if (StringUtils.isEmpty(id)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }
        VideoDto videoDto = new VideoDto();
        VideoOperation videoOperation = videoOperationService.getByAssociatedId(id,"1");
        Video video = videoService.getVideoById(id);
        BeanUtils.copyProperties(video,videoDto);
        if (ObjectUtils.isEmpty(videoOperation)) {
            videoDto.setLike(videoOperation.getLike());
        }
        return Result.setSpecialData(videoDto);
    }

    @Override
    public List<Video> getByIdIn(List<String> idList) {
        return videoService.getByIdIn(idList);
    }

    @Override
    @Transactional
    public Result<Object> addLikeCount(String id) {

        if (StringUtils.isEmpty(id)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }
        videoService.addLikeCountById(id);
        VideoOperation videoOperation = new VideoOperation();
        videoOperation.setId(UUID.randomUUID().toString());
        videoOperation.setAssociatedUserId("1");
        videoOperation.setAssociatedVideoId(id);
        videoOperation.setLike(DBConstant.YES);
        videoOperation.setCreateTime(new Date(System.currentTimeMillis()));
        videoOperation.setUpdateTime(new Date(System.currentTimeMillis()));
        videoOperationService.save(videoOperation);

        return Result.OK;
    }


    @Override
    public Result<Object> addWatchCount(String id) {

        if (StringUtils.isEmpty(id)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        videoService.addWatchCountById(id);
        return Result.OK;
    }
}
