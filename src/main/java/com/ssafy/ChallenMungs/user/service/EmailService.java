package com.ssafy.ChallenMungs.user.service;

import com.ssafy.ChallenMungs.user.entity.User;
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
        String subject = charityName + "님을 위한 초대코드 입니다";
        String htmlBody = "<html><body>" + 
                "<h5>안녕하세요" + charityName + "님!</5>" +
                "<h5>아래 비밀코드를 확인하여 회원가입을 진행해 주세요</h5><h1>"+
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

    public void sendHtmlEmail2(String to) {
        String code = String.valueOf((int)(Math.random()*899999) + 100000);
        User user = userService.findUserByLoginId(to);
        String name = user.getName();
        user.setPassword(code);
        userService.saveUser(user);
        String subject = name + "님, 챌린멍스에서 보내는 임시 비밀번호입니다";
        String htmlBody = "<html><body>" +
                "<h5>안녕하세요" +
                "" + name + "님!</5>" +
                "<h5>아래의 임시 비밀번호로 로그인을 진행해 주세요</h5><h1>"+
                code + "</h1>" +
                "<button onclick='goApp();'>로그인하러가기</button><script>" +
                "var goApp = () => { alert('myapp://') } </script>" +
                "</body></html>";

//        <intent-filter>
//            <action android:name="android.intent.action.VIEW" />
//            <category android:name="android.intent.category.DEFAULT" />
//            <category android:name="android.intent.category.BROWSABLE" />
//            <data android:scheme="myapp" />
//        </intent-filter>
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