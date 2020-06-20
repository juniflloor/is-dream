package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.ImageUiSettingService;
import is.dream.dao.entiry.ImageUiSetting;
import is.dream.dao.inter.ImageUiSettingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 4:27
 */
@Service
public class ImageUiSettingServiceImpl implements ImageUiSettingService {

    @Autowired
    private ImageUiSettingDao imageUiSettingDao;

    @Autowired
    public void updateByImageLocation(String imageLocation,String width, String high){
        imageUiSettingDao.updateByImageLocation(imageLocation,width,high);
    }

    @Autowired
    public void save(ImageUiSetting imageUiSetting){
        imageUiSettingDao.save(imageUiSetting);
    }
}
