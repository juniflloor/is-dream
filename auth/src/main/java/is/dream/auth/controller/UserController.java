package is.dream.auth.controller;

import is.dream.auth.service.UserBusinessService;
import is.dream.common.Result;
import is.dream.dao.entiry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/10 1:28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserBusinessService userBusinessService;

    @GetMapping( value = "/login")
    public Result<Object> login(@RequestParam("userName") String userName,@RequestParam("password") String password){

        return userBusinessService.login(userName,password);
    }

    @PostMapping( value = "/register")
    public Result<Object> register(@RequestBody User user){

        return null;
    }

    @PostMapping( value = "/logout")
    public Result<Object> logout(@RequestBody User user){

        return null;
    }

    @PostMapping( value = "/isLogin")
    public Result<Object> isLogin(@RequestBody User user){

        return null;
    }

    @PostMapping( value = "/isLawful")
    public Result<Object> isLawful(@RequestParam("token") String token){
        return userBusinessService.isLawful(token);
    }
}
