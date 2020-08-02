package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.LiveVideoService;
import is.dream.dao.entiry.LiveVideo;
import is.dream.dao.inter.LiveVideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/8/1 0:05
 */
@Service
public class LiveVideoServiceImpl implements LiveVideoService {

    @Autowired
    private LiveVideoDao liveVideoDao;

    @Override
    public void save(LiveVideo liveVideo) {
        liveVideoDao.save(liveVideo);
    }

    @Override
    public List<LiveVideo> getLiveVideo() {
        return liveVideoDao.getLiveVideo();
    }

    @Override
    public LiveVideo getLiveVideoByOrderBy(int orderBy) {
        return liveVideoDao.getLiveVideoByOrderBy(orderBy);
    }

    @Override
    public void endVideo() {
         liveVideoDao.endVideo();
    }

    @Override
    public void resetIsPlay() {
         liveVideoDao.resetIsPlay();
    }

    @Autowired
    public Integer getMaxOrderBy(){
        return liveVideoDao.getMaxOrderBy();
    }


    public List<LiveVideo> getLiveVideoList(){
        return liveVideoDao.getLiveVideoList();
    }
}
