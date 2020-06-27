package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.VideoCommentService;
import is.dream.dao.entiry.VideoComment;
import is.dream.dao.inter.VideoCommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/27 1:42
 */
@Service
public class VideoCommentServiceImpl implements VideoCommentService {

    @Autowired
    private VideoCommentDao videoCommentDao;

    @Override
    public void save(VideoComment videoComment){
        videoCommentDao.save(videoComment);
    }

    @Override
    public List<VideoComment> getById(String id, int startIndex){
        return videoCommentDao.getById(id,startIndex);
    }

    @Override
    public VideoComment getByCommentId(String commentId) {
        return videoCommentDao.getByCommentId(commentId);
    }

    @Override
    public List<VideoComment> getByIdCommentIdIn(List<String> commentSessionIdList){
        return videoCommentDao.getByIdCommentIdIn(commentSessionIdList);
    }

}
