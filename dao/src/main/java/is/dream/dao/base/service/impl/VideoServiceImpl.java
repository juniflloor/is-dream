package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.VideoService;
import is.dream.dao.entiry.Video;
import is.dream.dao.inter.VideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
