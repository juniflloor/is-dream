package is.dream.media.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import is.dream.common.Result;;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/8/1 0:21
 */
public interface LiveVideoBusinessService {

    Result<Object> save(String videoId,int orderBy);

    Result<Object> getCurrentLiveVideo();

    Result<Object> startLiveVideo(int orderBy, boolean isStart) throws JsonProcessingException;

    Result<Object> endLiveVideo();
}
