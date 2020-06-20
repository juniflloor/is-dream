package is.dream.media.controller;

import is.dream.common.Result;
import is.dream.dao.entiry.ImageUiSetting;
import is.dream.media.service.ImageUiSettingBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 4:53
 */
@RestController
@RequestMapping("/imageUiSetting")
public class ImageUiSettingController {

    @Autowired
    private ImageUiSettingBusinessService imageUiSettingBusinessService;

    public Result<Object> updateByImageLocation(@RequestParam("imageLocation") String imageLocation, @RequestParam("width") String width, @RequestParam("high") String high){
        return imageUiSettingBusinessService.updateByImageLocation(imageLocation,width,high);
    }

    public Result<Object> save(@RequestBody ImageUiSetting imageUiSetting){
        return imageUiSettingBusinessService.save(imageUiSetting);
    }
}
