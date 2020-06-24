package is.dream.media.service;

import is.dream.common.Result;
import is.dream.dao.entiry.ImageUiSetting;
import is.dream.dao.entiry.Video;
import is.dream.media.exception.MediaBusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 15:42
 */
public interface VideoBusinessService {

    Result<Object> upload(MultipartFile file,String name,String type,String tag,String title,String subtitle,String year,
                          String introduction, String startTime,boolean isGenerateUiImage, ImageUiSetting imageUiSetting) throws MediaBusinessException;

    Result<Object> getVideoById(String id);

    List<Video> getByIdIn(List<String> idList);
}
