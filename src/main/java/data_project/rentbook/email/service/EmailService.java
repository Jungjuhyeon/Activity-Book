package data_project.rentbook.email.service;

import data_project.rentbook.global.exception.BusinessException;
import data_project.rentbook.global.exception.errorcode.CommonErrorCode;
import data_project.rentbook.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final RedisUtil redisUtil;
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String email;


    public void sendMail(String email, String text){
        String authNum = getCertificationNumber();

        String title = "이메일 인증안내";

        String url;
        if (text.equals("비밀번호 재설정")){
            url = "changepassword";
        }else
            url = "emailverify";

        String content = "이메인 인증코드:" + authNum;
        MimeMessage emailForm = createEmailForm(email, title, content);

        mailSender.send(emailForm);
        redisUtil.setDataExpire("AuthCode_"+email, authNum,60*3);
    }


    private MimeMessage createEmailForm(String toEmail, String title, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(title);
            helper.setText(content, true);  // HTML 형식으로 설정
            helper.setFrom(email);
            return mimeMessage;
        }catch (MessagingException e){
            throw new BusinessException(CommonErrorCode.EMAIL_SEND_ERROR);
        }
    }


    //6자리 난수 생성
    public String getCertificationNumber() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder result = new StringBuilder();

            // 6자리의 인증번호 생성
            int digit = 0;
            for (int i = 0; i < 6; i++) {
                result.append(random.nextInt(10));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(CommonErrorCode.NO_SUCH_ALGORITHM);
        }

    }

}

