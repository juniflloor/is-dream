package is.dream.media.service.impl;

import is.dream.common.Result;
import is.dream.common.exception.BaseBusinessException;
import is.dream.common.exception.BaseExceptionCode;
import is.dream.dao.base.service.ImageUiSettingService;
import is.dream.dao.entiry.ImageUiSetting;
import is.dream.media.service.ImageUiSettingBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.util.UUID;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 4:38
 */
@Service
public class ImageUiSettingBusinessServiceImpl implements ImageUiSettingBusinessService {

    @Autowired
    private ImageUiSettingService imageUiSettingService;

    @Override
    public Result<Object> updateByImageLocation(String imageLocation, int width, int high,String remark){

        if (StringUtils.isEmpty(imageLocation) || StringUtils.isEmpty(width) || StringUtils.isEmpty(high)) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }
        Date updateTime = new Date(System.currentTimeMillis());
        imageUiSettingService.updateByImageLocation(imageLocation,width,high,remark,updateTime);

        return Result.OK;
    }

    @Override
    public Result<Object> save(ImageUiSetting imageUiSetting){

        if (StringUtils.isEmpty(imageUiSetting.getHigh()) || StringUtils.isEmpty(imageUiSetting.getWidth()) ||
            StringUtils.isEmpty(imageUiSetting.getImageLocation())) {
            throw new BaseBusinessException(BaseExceptionCode.B_PARAM_FAIL);
        }
        imageUiSetting.setId(UUID.randomUUID().toString());
        imageUiSetting.setCreateTime(new Date(System.currentTimeMillis()));
        imageUiSetting.setUpdateTime(new Date(System.currentTimeMillis()));
        imageUiSettingService.save(imageUiSetting);

        return Result.OK;
    }
}
