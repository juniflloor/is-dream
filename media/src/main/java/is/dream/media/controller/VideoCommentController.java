package is.dream.media.controller;

import is.dream.common.Result;
import is.dream.media.service.VideoCommentBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/27 2:03
 */
@RestController
@RequestMapping("/videoComment")
public class VideoCommentController {

    @Autowired
    private VideoCommentBusinessService videoCommentBusinessService;

    @PostMapping("/saveComment")
    public Result<Object> saveComment(@RequestParam("videoId") String videoId, @RequestParam("content") String content,@RequestParam("parentId") String parentId){

        return videoCommentBusinessService.saveComment(videoId,content,parentId);
    }

    @PostMapping("/getVoidComment")
    public Result<Object> getVoidComment(@RequestParam("videoId") String videoId, @RequestParam("startIndex") int startIndex){

        return videoCommentBusinessService.getVoidComment(videoId,startIndex);
    }

    @PostMapping("/getCommentByUserId")
    public Result<Object> getCommentByUserId(@RequestParam("associatedUserId") String associatedUserId){

        return videoCommentBusinessService.getCommentByUserId(associatedUserId);
    }


}
