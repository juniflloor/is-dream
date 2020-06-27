package is.dream.media.service.impl;

import is.dream.common.Result;
import is.dream.common.constants.DBConstant;
import is.dream.common.exception.BaseBusinessException;
import is.dream.common.exception.BaseExceptionCode;
import is.dream.dao.base.service.UserService;
import is.dream.dao.base.service.VideoCommentService;
import is.dream.dao.entiry.User;
import is.dream.dao.entiry.VideoComment;
import is.dream.media.dto.VideoCommentDto;
import is.dream.media.exception.MediaBusinessException;
import is.dream.media.exception.MediaBusinessExceptionCode;
import is.dream.media.service.VideoCommentBusinessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/27 2:18
 */
@Service
public class VideoCommentBusinessServiceImpl implements VideoCommentBusinessService {

    @Autowired
    private VideoCommentService videoCommentService;

    @Autowired
    private UserService userService;

    @Override
    public Result<Object> saveComment(String videoId, String content, String parentId) {

        VideoComment videoComment = new VideoComment();
        String commentId = UUID.randomUUID().toString();
        videoComment.setCommentId(commentId);
        videoComment.setUserId("root");
        if (StringUtils.isEmpty(parentId)) {
            videoComment.setCommentSessionId(commentId);
        } else {
            VideoComment parentVideoComment = videoCommentService.getByCommentId(parentId);
            if (ObjectUtils.isEmpty(parentVideoComment)) {
                throw new MediaBusinessException(MediaBusinessExceptionCode.VIDEO_COMMENT_IS_NOT_FOUND);
            }
            videoComment.setCommentSessionId(parentVideoComment.getCommentSessionId());
        }
        videoComment.setContent(content);
        videoComment.setParentId(parentId);
        videoComment.setCreateTime(new Date(System.currentTimeMillis()));
        videoCommentService.save(videoComment);
        videoComment.setCommentId(UUID.randomUUID().toString());

        return Result.OK;
    }

    @Override
    public Result<Object> getVoidComment(String videoId, int startIndex){

        if (StringUtils.isEmpty(videoId) || StringUtils.isEmpty(startIndex)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }

        List<VideoComment> topVideoCommentList = videoCommentService.getById(videoId,startIndex);
        if (CollectionUtils.isEmpty(topVideoCommentList)) {
            return Result.OK;
        }
        List<String> commentSessionIdList = topVideoCommentList.stream().map(VideoComment::getCommentSessionId).collect(Collectors.toList());
        List<VideoComment> videoCommentList = videoCommentService.getByIdCommentIdIn(commentSessionIdList);
        List<String> userIdList = videoCommentList.stream().map(VideoComment::getUserId).collect(Collectors.toList());
        List<User> userList = userService.getByIdIn(userIdList);
        Map<String,User> userMap = userList.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
        List<VideoCommentDto> videoCommentDtoList = new ArrayList<>();
        videoCommentList.forEach(videoComment -> {
            VideoCommentDto videoCommentDto = new VideoCommentDto();
            BeanUtils.copyProperties(videoComment,videoCommentDto);
            User user = userMap.get(videoComment.getUserId());
            videoCommentDto.setUserName(user.getUserName());
            videoCommentDto.setHeadImageUrl(user.getUserHeadImageUrl());
            videoCommentDtoList.add(videoCommentDto);
        });
        Map<String,List<VideoCommentDto>> videoCommentDtoMap = videoCommentDtoList.stream().collect(Collectors.groupingBy(VideoComment::getCommentSessionId));

        List<VideoCommentDto> returnList = new ArrayList<>();
        videoCommentDtoMap.forEach((k,v) -> {
            Map<String,List<VideoCommentDto>> perVideoCommentDtoMap = videoCommentDtoList.stream().collect(Collectors.groupingBy(VideoComment::getParentId));
            VideoCommentDto finalVideoCommentDto = perVideoCommentDtoMap.get(DBConstant.COMMENT_FIRST_PARENT).get(0);
            generate(perVideoCommentDtoMap,finalVideoCommentDto);
            returnList.add(finalVideoCommentDto);
        });

        return Result.setSpecialData(returnList);
    }

    private void generate(Map<String,List<VideoCommentDto>> perVideoCommentDtoMap,VideoCommentDto currentVideoCommentDto){

        String parentId = currentVideoCommentDto.getCommentId();
        List<VideoCommentDto> videoCommentDtoList = perVideoCommentDtoMap.get(parentId);
        currentVideoCommentDto.setCommentDtoList(videoCommentDtoList);
        videoCommentDtoList.forEach(videoCommentDto -> {
            String nextParentId = currentVideoCommentDto.getCommentId();
            if (!ObjectUtils.isEmpty(perVideoCommentDtoMap.get(nextParentId))) {
                generate(perVideoCommentDtoMap,videoCommentDto);
            }
        });
    }
}
