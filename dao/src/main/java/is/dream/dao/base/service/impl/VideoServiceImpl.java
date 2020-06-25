package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.VideoService;
import is.dream.dao.entiry.Video;
import is.dream.dao.inter.VideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 18:55
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    @Override
    public void saveFull(Video video){
         videoDao.saveFull(video);
    }

    @Override
    public Video getVideoById(String id) {
        return videoDao.getVideoById(id);
    }

    @Override
    public List<Video> getByIdIn(List<String> idList) {
        return videoDao.getByIdIn(idList);
    }

    @Override
    public void addWatchCountById(String id){ videoDao.addWatchCountById(id); }

    @Override
    public void addLikeCountById(String id){ videoDao.addLikeCountById(id); }
}
