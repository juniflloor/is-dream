package is.dream.dao.base.service;

import is.dream.dao.entiry.ImageUiSetting;

import java.sql.Date;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 4:37
 */
public interface ImageUiSettingService {

    void updateByImageLocation(String imageLocation, int width, int high,String remark,Date updateTime);

    void save(ImageUiSetting imageUiSetting);

    ImageUiSetting getByImageLocation(String imageLocation);
}
