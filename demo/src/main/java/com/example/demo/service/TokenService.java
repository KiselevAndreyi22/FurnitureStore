package com.example.demo.service;

import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {
    private final TokenRepository tokenRepository;

    public void saveToken(String accesToken, String refreshToken, User user) {
        Token token = new Token();

        token.setAccessToken(accesToken);

        token.setRefreshToken(refreshToken);

        token.setUser(user);

        tokenRepository.save(token);
    }
}
