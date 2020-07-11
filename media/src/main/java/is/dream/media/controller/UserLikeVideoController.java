package is.dream.media.controller;

import is.dream.common.Result;
import is.dream.dao.entiry.UserLikeVideo;
import is.dream.media.service.UserLikeVideoBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 15:48
 */
@RestController
@RequestMapping("/userLikeVideo")
public class UserLikeVideoController {

    @Autowired
    private UserLikeVideoBusinessService userLikeVideoBusinessService;

    public Result<Object> save(@RequestBody UserLikeVideo userLikeVideo){
        return userLikeVideoBusinessService.save(userLikeVideo);
    }

    @GetMapping("/getByAssociatedUserId")
    public Result<Object> getByAssociatedUserId(@RequestParam("associatedUserId") String associatedUserId){
        return userLikeVideoBusinessService.getByAssociatedUserId(associatedUserId);
    }
}
