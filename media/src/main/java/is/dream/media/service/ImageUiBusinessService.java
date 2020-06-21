package is.dream.media.service;

import is.dream.common.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 22:45
 */
public interface ImageUiBusinessService {

    Result<Object> generateImageUrl(MultipartFile file, String title, String introduction, String startTime, int width, int high, String imageLocation);

    Result<Object> getImageUiByImageLocation(String imageLocation);
}
