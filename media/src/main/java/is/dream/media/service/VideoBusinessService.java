package is.dream.media.service;

import is.dream.common.Result;
import is.dream.media.exception.MediaBusinessException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 15:42
 */
public interface VideoBusinessService {

    Result<Object> upload(MultipartFile file, String title,String introduction) throws MediaBusinessException;
}
