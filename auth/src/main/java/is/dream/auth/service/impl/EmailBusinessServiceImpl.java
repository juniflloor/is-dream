package is.dream.auth.service.impl;

import is.dream.auth.exception.AuthBusinessException;
import is.dream.auth.exception.AuthBusinessExceptionCode;
import is.dream.auth.service.EmailBusinessService;
import is.dream.common.Result;
import is.dream.common.constants.EmailConstant;
import is.dream.common.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/6/30 5:40
 */
@Service
public class EmailBusinessServiceImpl implements EmailBusinessService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public Result<Object> sendSimpleMail(String to) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(EmailConstant.CREATE_ACCOUNT_REGISTER);
            String content = EmailConstant.CREATE_ACCOUNT_REGISTER_CODE + CodeUtil.getCode() + EmailConstant.CODE_INVALID_EXPIRE;
            message.setText(content);
            javaMailSender.send(message);
        } catch (Exception e) {

            throw new AuthBusinessException(AuthBusinessExceptionCode.EMAIL_SEND_FAIL);
        }

        return Result.OK;
    }

}
