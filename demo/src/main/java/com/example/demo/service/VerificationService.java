package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationService {

    private final JavaMailSender mailSender;

    @Autowired
    public VerificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private Map<String, String> verificationCodes = new HashMap<>();

    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kiselev.rok-stargames@mail.ru");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendVerificationEmail(String toEmail) {
        String code = generateVerificationCode();
        verificationCodes.put(toEmail, code);

        String subject = "Ваш код подтверждения";
        String body = "Ваш код подтверждения: " + code;

        sendSimpleEmail(toEmail, subject, body);
    }

    public boolean verifyCode(String toEmail, String code) {
        String correctCode = verificationCodes.get(toEmail);
        if (correctCode != null && correctCode.equals(code)) {
            verificationCodes.remove(toEmail);
            return true;
        }
        return false;
    }
}
