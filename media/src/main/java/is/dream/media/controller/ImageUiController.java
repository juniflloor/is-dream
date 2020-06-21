package is.dream.media.controller;

import is.dream.common.Result;
import is.dream.media.service.ImageUiBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/21 20:34
 */
@RestController
@RequestMapping("/imageUi")
public class ImageUiController {

    @Autowired
    private ImageUiBusinessService imageUiBusinessService;

    public Result<Object> generateImageUrl(@ModelAttribute("file") MultipartFile file, @RequestParam("title") String title,
                                           @RequestParam("introduction") String introduction,@RequestParam("startTime") String startTime,
                                           @RequestParam("width") int width,@RequestParam("high") int high, @RequestParam("imageLocation") String imageLocation){
        return imageUiBusinessService.generateImageUrl(file,title,introduction,startTime,width,high,imageLocation);
    }
}
