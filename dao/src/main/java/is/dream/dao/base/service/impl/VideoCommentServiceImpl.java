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
    public List<VideoComment> getByVideoId(String videoId, int startIndex){
        return videoCommentDao.getByVideoId(videoId,startIndex);
    }

    @Override
    public VideoComment getById(String id) {
        return videoCommentDao.getById(id);
    }

    @Override
    public List<VideoComment> getByCommentSessionIdIn(List<String> commentSessionIdList){
        return videoCommentDao.getByCommentSessionIdIn(commentSessionIdList);
    }

    @Override
    public List<VideoComment> getByCommentSessionId(String commentSessionId){
        return videoCommentDao.getByCommentSessionId(commentSessionId);
    }
}
