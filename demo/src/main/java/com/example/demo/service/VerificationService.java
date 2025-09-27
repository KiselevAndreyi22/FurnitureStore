package com.example.demo.service;

import com.example.demo.exception.InvalidVerifyCodeException;
import io.lettuce.core.RedisCommandExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationService {

    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;
    private final int CODE_TTL_MINUTES = 10;
    private final int MAX_ATTEMPTS = 5;

    @Autowired
    public VerificationService(JavaMailSender mailSender, RedisTemplate<String, String> redisTemplate) {
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
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

        String redisKey = "verification:" + toEmail;
        String attemptsKey = "attempts:" + toEmail;

        redisTemplate.opsForValue().set(redisKey, code, Duration.ofMinutes(CODE_TTL_MINUTES));
        redisTemplate.opsForValue().set(attemptsKey, "0", Duration.ofMinutes(CODE_TTL_MINUTES));

        verificationCodes.put(toEmail, code);

        String subject = "Ваш код подтверждения";
        String body = "Ваш код подтверждения: " + code;

        sendSimpleEmail(toEmail, subject, body);
    }

    public boolean verifyCode(String toEmail, String code) {
        String redisKey = "verification:" + toEmail;
        String attemptsKey = "attempts:" + toEmail;

        try {
            String correctCode = redisTemplate.opsForValue().get(redisKey);
            if (correctCode == null) {
                throw new InvalidVerifyCodeException();
            }

            String attemptsStr = redisTemplate.opsForValue().get(attemptsKey);
            int attempts = attemptsStr == null ? 0 : Integer.parseInt(attemptsStr);

            if (attempts >= MAX_ATTEMPTS) {
                return false;
            }

            if (correctCode.equals(code)) {
                redisTemplate.delete(redisKey);
                redisTemplate.delete(attemptsKey);
                return true;
            } else {
                redisTemplate.opsForValue().increment(attemptsKey);
                return false;
            }
        }
        catch (RedisCommandExecutionException e) {
            throw new InvalidVerifyCodeException();
        }
        /*if (correctCode == null) {
            throw new InvalidVerifyCodeException();
        }*/
    }
}
