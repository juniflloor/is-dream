package is.dream.media.service.impl;

import is.dream.common.Result;
import is.dream.common.constants.DBConstant;
import is.dream.common.exception.BaseBusinessException;
import is.dream.common.exception.BaseExceptionCode;
import is.dream.dao.base.service.ImageUiService;
import is.dream.dao.base.service.ImageUiSettingService;
import is.dream.dao.entiry.ImageUi;
import is.dream.dao.entiry.ImageUiSetting;
import is.dream.dao.entiry.Video;
import is.dream.media.config.VideoConfig;
import is.dream.media.dto.VideoDto;
import is.dream.media.exception.MediaBusinessException;
import is.dream.media.exception.MediaBusinessExceptionCode;
import is.dream.media.handler.ffmpeg.untils.SystemUtils;
import is.dream.media.service.ImageUiBusinessService;
import is.dream.media.service.VideoBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 20:40
 */
@Service
@Slf4j
public class ImageUiBusinessServiceImpl implements ImageUiBusinessService {

    @Autowired
    private ImageUiSettingService imageUiSettingService;

    @Autowired
    private VideoBusinessService videoBusinessService;

    @Autowired
    private ImageUiService imageUiService;

    @Autowired
    private VideoConfig videoConfig;

    @Override
    public Result<Object> generateImageUrl(MultipartFile file,String name,String type,String tag,String title,String subtitle,String year,
                                           String introduction, String startTime, String imageLocation){

        if (StringUtils.isEmpty(imageLocation)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        ImageUiSetting imageUiSetting = imageUiSettingService.getByImageLocation(imageLocation);
        if (StringUtils.isEmpty(imageUiSetting)) {
            throw  new MediaBusinessException(MediaBusinessExceptionCode.IMAGE_UI_SETTING_NOT_FOUND);
        }

        return videoBusinessService.upload(file,name,type,tag,title,subtitle,year,introduction,startTime,true,imageUiSetting);
    }

    @Override
    public Result<Object> getImageUiByImageLocation(String imageLocation) {

        if (StringUtils.isEmpty(imageLocation)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        List<ImageUiSetting> imageUiSettingList = imageUiSettingService.getByImageLocationLike(imageLocation);

        log.info("imageUiSettingList size {}",imageUiSettingList.size());
        if (ObjectUtils.isEmpty(imageUiSettingList)) {
            throw new MediaBusinessException(MediaBusinessExceptionCode.IMAGE_UI_SETTING_NOT_FOUND);
        }

        List<List<VideoDto>> videoDtoList = new ArrayList<>();
        if (imageUiSettingList.size() == DBConstant.YES) {
            ImageUiSetting imageUiSetting = imageUiSettingList.get(DBConstant.NO);
            List<ImageUi> imageUiList = imageUiService.getImageUiByAssociatedImageUiSettingId(imageUiSetting.getId());
            log.info("imageUiList size {}",imageUiList.size());
            List<String> associatedVideoIdList = imageUiList.stream().map(ImageUi::getAssociatedVideoId).collect(Collectors.toList());

            log.info("start videoBusinessService.getByIdIn");
            List<Video> videoList = videoBusinessService.getByIdIn(associatedVideoIdList);
            log.info("end videoBusinessService.getByIdIn videoList {}",videoList.size());
            Map<String, Video> videoMap = videoList.stream().collect(Collectors.toMap(Video::getId, v -> v));

            imageUiList.forEach(ImageUi -> {
                VideoDto videoDto = new VideoDto();
                videoDto.setImageUrl(ImageUi.getImageUrl());
                Video video = videoMap.get(ImageUi.getAssociatedVideoId());
                BeanUtils.copyProperties(video, videoDto);
                List<VideoDto> list = new ArrayList<>();
                list.add(videoDto);
                videoDtoList.add(list);
            });
        } else {

            List<String> stringImageLocationList = imageUiSettingList.stream().map(ImageUiSetting::getId).collect(Collectors.toList());
            List<ImageUi> imageUiList = imageUiService.getByAssociatedImageUiSettingIdList(stringImageLocationList);
            Map<String,List<ImageUi>> imageUiMap = imageUiList.stream().collect(Collectors.groupingBy(imageUi -> imageUi.getAssociatedImageUiSettingId()));

            List<String> associatedVideoIdList = imageUiList.stream().map(ImageUi::getAssociatedVideoId).collect(Collectors.toList());
            List<Video> videoList = videoBusinessService.getByIdIn(associatedVideoIdList);
            Map<String, Video> videoMap = videoList.stream().collect(Collectors.toMap(Video::getId, v -> v));

            boolean flag = true;

            while (flag) {

                try {
                    int fullCount = 0;
                    List<VideoDto> list = new ArrayList<>();
                    for (int i = 0; i < imageUiSettingList.size(); i++) {
                        ImageUiSetting imageUiSetting = imageUiSettingList.get(i);
                        int weight = imageUiSetting.getWeight();
                        fullCount += weight;
                        List<ImageUi> tempImageUiList = imageUiMap.get(imageUiSetting.getId()).subList(videoDtoList.size() * weight, (videoDtoList.size() + 1) * weight );
                        tempImageUiList.forEach(ImageUi -> {
                            VideoDto videoDto = new VideoDto();
                            videoDto.setImageUrl(ImageUi.getImageUrl());
                            Video video = videoMap.get(ImageUi.getAssociatedVideoId());
                            BeanUtils.copyProperties(video, videoDto);
                            list.add(videoDto);
                        });
                    }

                    if (fullCount == list.size()) {
                        videoDtoList.add(list);
                    } else {
                        flag = false;
                    }

                    if (fullCount * videoDtoList.size() == imageUiList.size()) {
                        flag = false;
                    }
                }catch (Exception e) {
                    flag = false;
                }
            }

        }

        return Result.setSpecialData(videoDtoList);
    }

    @Override
    public Result<Object> deleteImageUiByImageLocation(String imageLocation) {

        if (StringUtils.isEmpty(imageLocation)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        File imageUiPath = new File(videoConfig.getImageUIPath() + imageLocation);
        SystemUtils.deleteLocalFiles(imageUiPath);

        ImageUiSetting imageUiSetting = imageUiSettingService.getByImageLocation(imageLocation);
        if (ObjectUtils.isEmpty(imageUiSetting)) {
            throw new MediaBusinessException(MediaBusinessExceptionCode.IMAGE_UI_SETTING_NOT_FOUND);
        }

        imageUiService.deleteByAssociatedImageUiSettingId(imageUiSetting.getId());

        return Result.setSpecialData(null);
    }

}
