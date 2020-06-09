package is.dream.gate;

import is.dream.dao.base.service.UserService;
import is.dream.dao.entiry.User;
import is.dream.gate.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/9 19:09
 */
@RestController
public class TestController {

    @Resource
    private UserService userService;

    @GetMapping("/test")
    public Result<User> test(){
        User user = userService.getByUserNameAndPassword("dzchen","123456");
        return new Result<User>("0","success",user);
    }

}
