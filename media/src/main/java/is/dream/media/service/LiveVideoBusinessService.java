package is.dream.media.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import is.dream.common.Result;
import is.dream.dao.entiry.LiveVideo;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/8/1 0:21
 */
public interface LiveVideoBusinessService {

    Result<Object> save(String videoId);

    Result<Object> getCurrentLiveVideo();

    Result<Object> startLiveVideo() throws JsonProcessingException;

    Result<Object> endLiveVideo();
}
