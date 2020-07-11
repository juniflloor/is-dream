package is.dream.media.service.impl;

import is.dream.common.Result;
import is.dream.common.exception.BaseBusinessException;
import is.dream.common.exception.BaseExceptionCode;
import is.dream.dao.base.service.UserPlayVideoLogService;
import is.dream.dao.base.service.VideoService;
import is.dream.dao.entiry.UserPlayVideoLog;
import is.dream.dao.entiry.Video;
import is.dream.media.service.UserPlayVideoLogBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 12:28
 */
@Service
public class UserPlayVideoLogBusinessServiceImpl implements UserPlayVideoLogBusinessService {

    @Autowired
    private UserPlayVideoLogService userPlayVideoLogService;

    @Autowired
    private VideoService videoService;

    @Override
    public Result<Object> save(UserPlayVideoLog userPlayVideoLog) {
        if (ObjectUtils.isEmpty(userPlayVideoLog)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        userPlayVideoLog.setId(UUID.randomUUID().toString());
        userPlayVideoLog.setCreateTime(new Timestamp(new Date().getTime()));
        userPlayVideoLog.setUpdateTime(new Timestamp(new Date().getTime()));

        userPlayVideoLogService.save(userPlayVideoLog);

        return Result.OK;
    }

    @Override
    public Result<Object> getByAssociatedUserId(String associatedUserId) {

        List<UserPlayVideoLog> userPlayVideoLogList = userPlayVideoLogService.getByAssociatedUserId(associatedUserId);
        if (ObjectUtils.isEmpty(userPlayVideoLogList)) {
            return Result.OK;
        }

        List<String> videoIdList = userPlayVideoLogList.stream().map(UserPlayVideoLog::getAssociatedVideoId).collect(Collectors.toList());
        List<Video> videoList = videoService.getByIdIn(videoIdList);

        return Result.setSpecialData(videoList);
    }
}
