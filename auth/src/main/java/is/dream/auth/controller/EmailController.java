package is.dream.auth.controller;

import is.dream.auth.service.EmailBusinessService;
import is.dream.common.Result;
import is.dream.common.exception.BaseBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/30 5:54
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailBusinessService emailService;

    @GetMapping( value = "/send")
    public Result<Object> send(@RequestParam("to") String to) throws BaseBusinessException {

        return emailService.sendSimpleMail(to);
    }
}
