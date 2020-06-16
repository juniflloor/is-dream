package is.dream.media.controller;

import is.dream.common.Result;
import is.dream.media.exception.MediaBusinessException;
import is.dream.media.service.VideoBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/16 20:39
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoBusinessService videoBusinessService;

    @PostMapping("/upload")
    public Result<Object> upload(MultipartFile file, String title, String introduction) throws MediaBusinessException{

        return videoBusinessService.upload(file, title, introduction);
    }
}
