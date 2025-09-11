package com.example.demo.controller;

import com.example.demo.service.DataRecoveryService;
import com.example.demo.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private VerificationService emailService;

    @Autowired
    private DataRecoveryService dataRecoveryService;

    @PostMapping("/send")
    public String sendMail(@RequestParam String to,
                           @RequestParam String subject,
                           @RequestParam String body) {
        emailService.sendSimpleEmail(to, subject, body);
        return "Письмо отправлено!";
    }

    @PostMapping("/send-verify")
    public String sendMailVerify(@RequestParam String to){
        emailService.sendVerificationEmail(to);
        return "Код подтверждения отправлен на почту!";
    }
}
