package is.dream.dao.base.service;

import is.dream.dao.entiry.VideoOperation;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/25 18:54
 */
public interface VideoOperationService {

    void save(VideoOperation videoOperation);

    VideoOperation getByAssociatedId(String associatedVideoId,String associatedUserId);
}
