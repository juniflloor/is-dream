package is.dream.media.controller;

import is.dream.common.Result;
import is.dream.media.service.ImageUiBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/generateImageUrl")
    public Result<Object> generateImageUrl(@ModelAttribute("file") MultipartFile file, @RequestParam("name") String name,@RequestParam("type") String type,
                                           @RequestParam("tag") String tag, @RequestParam("title") String title,@RequestParam("subtitle") String subtitle,
                                           @RequestParam("year") String year,@RequestParam("introduction") String introduction,@RequestParam("startTime") String startTime,
                                           @RequestParam("imageLocation") String imageLocation){
        return imageUiBusinessService.generateImageUrl(file,name,type,tag,title,subtitle,year,introduction,startTime,imageLocation);
    }

    @GetMapping("/getImageUiByImageLocation")
    public Result<Object> getImageUiByImageLocation(@RequestParam("imageLocation") String imageLocation){
        return imageUiBusinessService.getImageUiByImageLocation(imageLocation);
    }

    @PostMapping("/deleteImageUiByImageLocation")
    public Result<Object> deleteImageUiByImageLocation(@RequestParam("imageLocation") String imageLocation){
        return null;
    }
}
