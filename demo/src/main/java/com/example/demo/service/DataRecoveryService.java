package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataRecoveryService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void updatePassword(Long userId, String password) {
        User user = userRepository.findById(userId).
                orElseThrow();

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }
}
