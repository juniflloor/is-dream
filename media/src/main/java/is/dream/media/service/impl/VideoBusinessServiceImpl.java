package is.dream.media.service.impl;

import is.dream.common.Result;
import is.dream.common.constants.DBConstant;
import is.dream.common.exception.BaseBusinessException;
import is.dream.common.exception.BaseExceptionCode;
import is.dream.common.utils.StringIUtils;
import is.dream.dao.base.service.*;
import is.dream.dao.entiry.*;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.Timestamp;
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

    @Autowired
    private UserPlayVideoLogService userPlayVideoLogService;

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
            video.setLeadRole("陈冬照");
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
    public Result<Object> getVideoById(String id,String userId) {

        if (StringUtils.isEmpty(id)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }
        VideoDto videoDto = new VideoDto();
        VideoOperation videoOperation = videoOperationService.getByAssociatedId(id,"1");
        Video video = videoService.getVideoById(id);
        BeanUtils.copyProperties(video,videoDto);
        if (!ObjectUtils.isEmpty(videoOperation)) {
            videoDto.setLike(videoOperation.getLike());
        }

        if (!StringUtils.isEmpty(userId)) {
            UserPlayVideoLog userPlayVideoLog = new UserPlayVideoLog();
            String userPlayVideoLogId = UUID.randomUUID().toString();
            userPlayVideoLog.setId(userPlayVideoLogId);
            userPlayVideoLog.setAssociatedUserId(userId);
            userPlayVideoLog.setAssociatedVideoId(id);
            userPlayVideoLog.setCreateTime(new Timestamp(new java.util.Date().getTime()));
            userPlayVideoLog.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
            userPlayVideoLogService.save(userPlayVideoLog);
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

    public  Result<Object> searchVideo(String keyword) throws UnsupportedEncodingException {

        if (StringUtils.isEmpty(keyword)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        String decodeKeyword = URLDecoder.decode(keyword,"utf-8");
        String likeParam = StringIUtils.addBothPercent(decodeKeyword);
        List<Video> videoList = videoService.searchVideo(likeParam,likeParam,likeParam,likeParam);

        if (ObjectUtils.isEmpty(videoList)) {
            return Result.OK;
        }

        return Result.setSpecialData(videoList);
    }

    @Override
    public Result<Object> getAssociatedVideo(String id) {

        if (StringUtils.isEmpty(id)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        Video video = videoService.getVideoById(id);

        if (ObjectUtils.isEmpty(video)) {
            return Result.OK;
        }

        String name = StringIUtils.addBothPercent(video.getName());
        String type = StringIUtils.addBothPercent(video.getType());
        String tag = StringIUtils.addBothPercent(video.getTag());
        String leadRole = StringIUtils.addBothPercent(video.getLeadRole());
        List<Video> videoList = videoService.searchVideo(name,type,tag,leadRole);

        if (ObjectUtils.isEmpty(videoList)) {
            return Result.OK;
        }

        return Result.setSpecialData(videoList);

    }

    @Override
    public Result<Object> getMostViewVideo() {
        List<Video> videoList =  videoService.getMostViewVideo();

        if (ObjectUtils.isEmpty(videoList)) {
            return Result.OK;
        }

        return Result.setSpecialData(videoList);
    }

}
