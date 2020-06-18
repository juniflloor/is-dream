package is.dream.dao.base.service;

import is.dream.dao.entiry.Video;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 18:58
 */
public interface VideoService {

    Video getByUserId(String id);

    void saveFull(Video video);

    Video getHighestScore();

    Video getHottest();

    List<Video> getNewest();
}
