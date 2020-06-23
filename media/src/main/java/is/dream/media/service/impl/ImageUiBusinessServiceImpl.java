package is.dream.media.service.impl;

import is.dream.common.Result;
import is.dream.common.exception.BaseBusinessException;
import is.dream.common.exception.BaseExceptionCode;
import is.dream.dao.base.service.ImageUiService;
import is.dream.dao.base.service.ImageUiSettingService;
import is.dream.dao.entiry.ImageUi;
import is.dream.dao.entiry.ImageUiSetting;
import is.dream.dao.entiry.Video;
import is.dream.media.dto.VideoDto;
import is.dream.media.exception.MediaBusinessException;
import is.dream.media.exception.MediaBusinessExceptionCode;
import is.dream.media.service.ImageUiBusinessService;
import is.dream.media.service.VideoBusinessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 20:40
 */
@Service
public class ImageUiBusinessServiceImpl implements ImageUiBusinessService {

    @Autowired
    private ImageUiSettingService imageUiSettingService;

    @Autowired
    private VideoBusinessService videoBusinessService;

    @Autowired
    private ImageUiService imageUiService;

    @Override
    public Result<Object> generateImageUrl(MultipartFile file, String title, String introduction, String startTime, int width, int high, String imageLocation){

        if (StringUtils.isEmpty(imageLocation)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        ImageUiSetting imageUiSetting = imageUiSettingService.getByImageLocation(imageLocation);
        if (StringUtils.isEmpty(imageUiSetting)) {
            throw  new MediaBusinessException(MediaBusinessExceptionCode.IMAGE_UI_SETTING_NOT_FOUND);
        }

        return videoBusinessService.upload(file, title, introduction,startTime,width,high,true,imageUiSetting);
    }

    @Override
    public Result<Object> getImageUiByImageLocation(String imageLocation) {

        if (StringUtils.isEmpty(imageLocation)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        ImageUiSetting imageUiSetting = imageUiSettingService.getByImageLocation(imageLocation);
        if (ObjectUtils.isEmpty(imageUiSetting)) {
            throw new MediaBusinessException(MediaBusinessExceptionCode.IMAGE_UI_SETTING_NOT_FOUND);
        }

        List<ImageUi> imageUiList = imageUiService.getImageUiByAssociatedImageUiSettingId(imageUiSetting.getId());
        List<String> associatedVideoIdList = imageUiList.stream().map(ImageUi::getAssociatedVideoId).collect(Collectors.toList());
        List<Video> videoList = videoBusinessService.getByIdIn(associatedVideoIdList);
        Map<String, Video> videoMap = videoList.stream().collect(Collectors.toMap(Video::getId, v->v));
        List<VideoDto> videoDtoList = new ArrayList<>();
        imageUiList.forEach(ImageUi-> {
            VideoDto videoDto = new VideoDto();
            videoDto.setImageUrl(ImageUi.getImageUrl());
            Video video = videoMap.get(ImageUi.getAssociatedVideoId());
            BeanUtils.copyProperties(video,videoDto);
            videoDtoList.add(videoDto);
        });

        return Result.setSpecialData(videoDtoList);
    }

}
