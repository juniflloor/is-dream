package is.dream.dao.base.service;

import is.dream.dao.entiry.VideoComment;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/27 2:22
 */
public interface VideoCommentService {

    void save(VideoComment videoComment);

    List<VideoComment> getByVideoId(String videoId, int startIndex);

    VideoComment getById(String id);

    List<VideoComment> getByCommentSessionIdIn(List<String> commentSessionIdList);

    List<VideoComment> getByCommentSessionId(String commentSessionId);
}
