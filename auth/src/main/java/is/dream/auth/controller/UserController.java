package is.dream.auth.controller;

import is.dream.auth.service.UserBusinessService;
import is.dream.common.Result;
import is.dream.dao.entiry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/10 1:28
 */
@RestController( value = "/userIdentity")
public class UserController {

    @Autowired
    private UserBusinessService userBusinessService;

    @PostMapping( value = "/login")
    public Result<Object> login(@RequestBody User user){

        return userBusinessService.login(user);
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
