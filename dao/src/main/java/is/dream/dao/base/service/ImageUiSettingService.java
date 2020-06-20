package is.dream.dao.base.service;

import is.dream.dao.entiry.ImageUiSetting;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 4:37
 */
public interface ImageUiSettingService {

    void updateByImageLocation(String imageLocation,String width, String high);

    void save(ImageUiSetting imageUiSetting);
}
