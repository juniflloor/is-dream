package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.VideoService;
import is.dream.dao.entiry.Video;
import is.dream.dao.inter.VideoDao;
import org.apache.ibatis.annotations.Select;
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
    public Video getByUserId(String id){
        return videoDao.getByUserId(id);
    }

    @Override
    public void saveFull(Video video){
         videoDao.saveFull(video);
    }

    @Override
    public Video getHighestScore(){
        return videoDao.getHighestScore();
    }

    @Override
    public Video getHottest(){
        return videoDao.getHottest();
    }

    @Override
    public List<Video> getNewest(){
        return videoDao.getNewest();
    }
}
