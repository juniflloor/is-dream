package is.dream.media.service.impl;

import is.dream.common.Result;
import is.dream.common.exception.BaseBusinessException;
import is.dream.common.exception.BaseExceptionCode;
import is.dream.dao.base.service.UserLikeVideoService;
import is.dream.dao.base.service.VideoService;
import is.dream.dao.entiry.UserLikeVideo;
import is.dream.dao.entiry.Video;
import is.dream.media.service.UserLikeVideoBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 15:32
 */
@Service
public class UserLikeVideoBusinessServiceImpl implements UserLikeVideoBusinessService {

    @Autowired
    private UserLikeVideoService userLikeVideoService;

    @Autowired
    private VideoService videoService;

    @Override
    public Result<Object> save(UserLikeVideo userLikeVideo) {

        if (ObjectUtils.isEmpty(userLikeVideo)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        userLikeVideo.setId(UUID.randomUUID().toString());
        userLikeVideo.setCreateTime(new Timestamp(new Date().getTime()));
        userLikeVideo.setUpdateTime(new Timestamp(new Date().getTime()));

       userLikeVideoService.save(userLikeVideo);
       return Result.setOk();
    }

    @Override
    public Result<Object> getByAssociatedUserId(String associatedUserId) {

        if (StringUtils.isEmpty(associatedUserId)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        List<UserLikeVideo> userLikeVideoList = userLikeVideoService.getByAssociatedUserId(associatedUserId);
        List<String> videoIdList = userLikeVideoList.stream().map(UserLikeVideo::getAssociatedVideoId).collect(Collectors.toList());
        List<Video> videoList = videoService.getByIdIn(videoIdList);
        return Result.setSpecialData(videoList);
    }
}
