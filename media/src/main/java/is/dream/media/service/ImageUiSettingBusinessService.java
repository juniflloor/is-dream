package is.dream.media.service;

import is.dream.common.Result;
import is.dream.dao.entiry.ImageUiSetting;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 4:53
 */
public interface ImageUiSettingBusinessService {
    Result<Object> updateByImageLocation(String imageLocation,int width, int high,String remark);

    Result<Object> save(ImageUiSetting imageUiSetting);
}
