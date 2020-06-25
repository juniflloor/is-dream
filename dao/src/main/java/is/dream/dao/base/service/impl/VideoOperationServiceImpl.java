package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.VideoOperationService;
import is.dream.dao.entiry.VideoOperation;
import is.dream.dao.inter.VideoOperationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/25 18:54
 */
@Service
public class VideoOperationServiceImpl implements VideoOperationService {

    @Autowired
    private VideoOperationDao videoOperationDao;

    @Override
    public void save(VideoOperation videoOperation){
        videoOperationDao.save(videoOperation);
    }

    @Override
    public VideoOperation getByAssociatedId(String associatedVideoId,String associatedUserId){
        return videoOperationDao.getByAssociatedId(associatedVideoId,associatedUserId);
    }
}
