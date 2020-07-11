package is.dream.media.controller;

import is.dream.common.Result;
import is.dream.dao.entiry.UserPlayVideoLog;
import is.dream.media.service.UserPlayVideoLogBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 12:43
 */
@RestController
@RequestMapping("/userPlayVideoLog")
public class UserPlayVideoLogController {

    @Autowired
    private UserPlayVideoLogBusinessService userPlayVideoLogBusinessService;

    @PostMapping("/save")
    public Result<Object> save(@RequestBody UserPlayVideoLog userPlayVideoLog){
        return userPlayVideoLogBusinessService.save(userPlayVideoLog);
    }

    @GetMapping("/getByAssociatedUserId")
    public Result<Object> getByAssociatedUserId(@RequestParam("associatedUserId") String associatedUserId){
        return userPlayVideoLogBusinessService.getByAssociatedUserId(associatedUserId);
    }
}
