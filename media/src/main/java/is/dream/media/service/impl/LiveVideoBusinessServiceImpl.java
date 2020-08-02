package is.dream.media.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import is.dream.common.Result;
import is.dream.common.constants.DBConstant;
import is.dream.common.exception.BaseBusinessException;
import is.dream.common.exception.BaseExceptionCode;
import is.dream.dao.base.service.LiveVideoService;
import is.dream.dao.base.service.VideoService;
import is.dream.dao.entiry.LiveVideo;
import is.dream.dao.entiry.Video;
import is.dream.media.config.VideoConfig;
import is.dream.media.exception.MediaBusinessException;
import is.dream.media.exception.MediaBusinessExceptionCode;
import is.dream.media.mq.kafka.KafkaProducer;
import is.dream.media.service.LiveVideoBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/8/1 0:21
 */
@Service
public class LiveVideoBusinessServiceImpl implements LiveVideoBusinessService {

    @Autowired
    private VideoService videoService;

    @Autowired
    private LiveVideoService liveVideoService;

    @Autowired
    private VideoConfig videoConfig;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public Result<Object> save(String videoId,int orderBy){

        if (StringUtils.isEmpty(videoId)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        Video video = videoService.getVideoById(videoId);
        if (ObjectUtils.isEmpty(video)) {
            throw  new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_DB_IS_OUT_FOUNT);
        }

        LiveVideo liveVideo = new LiveVideo();
        String id = UUID.randomUUID().toString();
        liveVideo.setId(id);
        liveVideo.setAssociatedVideoId(video.getId());
        liveVideo.setIsPlay(DBConstant.YES);
        liveVideo.setOrderBy(orderBy);

        liveVideoService.save(liveVideo);
        return Result.setOk();
    }

    @Override
    public Result<Object> getCurrentLiveVideo(){

        return Result.setSpecialData(videoConfig.getPullUrl());
    }

    @Override
    public Result<Object> startLiveVideo(int orderBy,boolean isStart) throws JsonProcessingException {

        if (isStart) {
            liveVideoService.resetIsPlay();
        }

        int maxOrderBy = liveVideoService.getMaxOrderBy();
        if (orderBy == maxOrderBy) {
            orderBy = DBConstant.NO;
        }

        LiveVideo liveVideo = liveVideoService.getLiveVideoByOrderBy(orderBy);

        if (ObjectUtils.isEmpty(liveVideo)) {
            throw new  MediaBusinessException(MediaBusinessExceptionCode.VIDEO_lIVE_START_OUT_FOUNT);
        }

        Video video = videoService.getVideoById(liveVideo.getAssociatedVideoId());
        if (ObjectUtils.isEmpty(video)) {
            throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_DB_IS_OUT_FOUNT);
        }

        String filePath = videoConfig.getSourcePath() + File.separator + video.getFileName();
        Map<String,String> kafkaMap = new HashMap<>();
        kafkaMap.put("filePath",filePath);
        kafkaMap.put("orderBy", String.valueOf(orderBy));
        kafkaProducer.send(kafkaMap);

        return Result.setOk();
    }

    @Override
    public Result<Object> endLiveVideo() {
        liveVideoService.endVideo();
        return Result.setOk();
    }
}
