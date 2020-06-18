package is.dream.media.service;

import is.dream.common.Result;
import is.dream.media.exception.MediaBusinessException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 15:42
 */
public interface VideoBusinessService {

    Result<Object> upload(MultipartFile file, String title,String introduction,String startTime,int width,int high) throws MediaBusinessException;

    Result<Object> getSift();

    Result<Object> getVideoById(String id);
}
