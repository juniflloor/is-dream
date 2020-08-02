package is.dream.dao.base.service;

import is.dream.dao.entiry.LiveVideo;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/8/1 0:05
 */
public interface LiveVideoService {

    void save(LiveVideo liveVideo);

    List<LiveVideo> getLiveVideo();

    LiveVideo getLiveVideoByOrderBy(int orderBy);

    void endVideo();

    void resetIsPlay();

    Integer getMaxOrderBy();

    List<LiveVideo> getLiveVideoList();
}
