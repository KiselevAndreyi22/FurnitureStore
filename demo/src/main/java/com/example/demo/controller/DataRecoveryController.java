package com.example.demo.controller;

import com.example.demo.dto.request.UpdatePasswordRequest;
import com.example.demo.model.User;
import com.example.demo.security.SecurityUtil;
import com.example.demo.service.DataRecoveryService;
import com.example.demo.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recovery")
public class DataRecoveryController {

    @Autowired
    VerificationService verificationService;

    @Autowired
    DataRecoveryService dataRecoveryService;


    @PostMapping("/update-password")
    public String verifyMail(@RequestBody UpdatePasswordRequest verifyEmailCodeRequest) {
        User user = SecurityUtil.getCurrentUser();

        boolean verify = verificationService.verifyCode(user.getEmail(), verifyEmailCodeRequest.getCode());
        if (verify) {
            dataRecoveryService.updatePassword(user.getId(), verifyEmailCodeRequest.getPassword());
            return "Подтверждено и пароль успешно изменён!";
        } else {
            return "Неверный код подтверждения!";
        }
    }
}
