package is.dream.auth.service.impl;

import is.dream.auth.exception.AuthBusinessException;
import is.dream.auth.exception.AuthBusinessExceptionCode;
import is.dream.auth.service.EmailBusinessService;
import is.dream.cache.utils.RedisUtils;
import is.dream.common.Result;
import is.dream.common.constants.EmailConstant;
import is.dream.common.utils.CodeIUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EmailBusinessServiceImpl implements EmailBusinessService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public Result<Object> sendSimpleMail(String to) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(EmailConstant.REGISTER_CODE_SUBJECT);
            String code =  CodeIUtil.getCode();
            String content = EmailConstant.REGISTER_CODE_CONTENT_FIRST + code + EmailConstant.REGISTER_CODE_CONTENT_SECOND;
            message.setText(content);
            javaMailSender.send(message);
            redisUtils.set(to,code,EmailConstant.REGISTER_CODE_EXPIRE);
        } catch (Exception e) {
            log.error("send email error {}",e);
            throw new AuthBusinessException(AuthBusinessExceptionCode.EMAIL_SEND_FAIL);
        }

        return Result.setOk();
    }

}
