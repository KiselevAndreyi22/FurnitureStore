package com.example.demo.controller;

import com.example.demo.dto.request.SignInRequest;
import com.example.demo.dto.request.SignUpRequest;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.model.Role;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody SignInRequest signInRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Authentication failed.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userService.getByUsername(signInRequest.getUsername());

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<JwtResponse> signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {
       User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .build();

        userService.create(user);

        var token = jwtService.generateToken(user);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
