package is.dream.auth.controller;

import is.dream.common.Result;
import is.dream.dao.entiry.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/10 1:28
 */
@RestController( value = "/userIdentity")
public class UserController {

    @PostMapping( value = "/login")
    public Result<Boolean> login(@RequestBody User user){

        return null;
    }

    @PostMapping( value = "/register")
    public Result<Boolean> register(@RequestBody User user){

        return null;
    }

    @PostMapping( value = "/logout")
    public Result<Boolean> logout(@RequestBody User user){

        return null;
    }

    @PostMapping( value = "/isLogin")
    public Result<Boolean> isLogin(@RequestBody User user){

        return null;
    }
}
