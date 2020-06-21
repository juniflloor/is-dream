package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.ImageUiSettingService;
import is.dream.dao.entiry.ImageUiSetting;
import is.dream.dao.inter.ImageUiSettingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 4:27
 */
@Service
public class ImageUiSettingServiceImpl implements ImageUiSettingService {

    @Autowired
    private ImageUiSettingDao imageUiSettingDao;

    @Override
    public void updateByImageLocation(String imageLocation, int width, int high,String remark,Date updateTime){
        imageUiSettingDao.updateByImageLocation(imageLocation,width,high,remark,updateTime);
    }

    @Override
    public void save(ImageUiSetting imageUiSetting){
        imageUiSettingDao.save(imageUiSetting);
    }
}
