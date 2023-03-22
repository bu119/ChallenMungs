package com.ssafy.ChallenMungs.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserService userService;

    @Autowired
    CodeService codeService;
    public void sendHtmlEmail(String to, String charityName) {
        String code = String.valueOf((int)(Math.random()*899999) + 100000);
        codeService.registerCode(charityName, code);
        String subject = "후원처용 초대코드 입니다";
        String htmlBody = "<html><body><h5>아래 비밀코드를 확인해 주세요</h5><h1>"+
                code + "</h1></body></html>";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(htmlBody);

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(message.getTo());
            messageHelper.setSubject(message.getSubject());
            messageHelper.setText(message.getText(), true);
            messageHelper.setFrom(new InternetAddress("ssafytukwa@gmail.com", "챌린멍스(do not reply)"));
        };

        javaMailSender.send(preparator);
    }
}