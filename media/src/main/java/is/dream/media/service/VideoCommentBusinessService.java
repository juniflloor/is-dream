package is.dream.media.service;

import is.dream.common.Result;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/27 2:25
 */
public interface VideoCommentBusinessService {

    Result<Object> saveComment(String videoId, String content, String parentId);

    Result<Object> getVoidComment(String videoId, int startIndex);

    Result<Object> getCommentByUserId(String associatedUserId);
}
