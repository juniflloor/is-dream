package is.dream.media.service;

import is.dream.common.Result;
import is.dream.dao.entiry.ImageUiSetting;
import is.dream.dao.entiry.Video;
import is.dream.media.exception.MediaBusinessException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 15:42
 */
public interface VideoBusinessService {

    Result<Object> upload(MultipartFile file,String name,String type,String tag,String title,String subtitle,String year,
                          String introduction, String startTime,boolean isGenerateUiImage, ImageUiSetting imageUiSetting) throws MediaBusinessException;

    Result<Object> getVideoById(String id,String userId);

    List<Video> getByIdIn(List<String> idList);

    Result<Object> addLikeCount(String id);

    Result<Object> addWatchCount(String id);

    Result<Object> searchVideo(String keyword) throws UnsupportedEncodingException;
}
