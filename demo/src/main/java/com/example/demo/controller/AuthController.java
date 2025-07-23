package com.example.demo.controller;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.request.SignInRequest;
import com.example.demo.dto.request.SignUpRequest;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.model.Role;
import com.example.demo.service.JwtService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.demo.model.User;
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
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody SignInRequest signInRequest) throws Exception {
        User user = userService.getByUsernameOrEmail(signInRequest.getUsernameOrEmail(), signInRequest.getUsernameOrEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), signInRequest.getPassword()));

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Authentication failed.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accesToken = jwtService.generateAccesToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        tokenService.saveToken(refreshToken, user);

        return ResponseEntity.ok(new JwtResponse(accesToken, refreshToken));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {
       User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .build();

        userService.create(user);

        return ResponseEntity.ok("Succes registered");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws Exception {
        String requestRefreshTokenRefreshToken = refreshTokenRequest.getRefreshToken();

        String username = jwtService.extractUsername(requestRefreshTokenRefreshToken);

        User user = userService.getByUsername(username);

        if(!jwtService.validateRefreshToken(requestRefreshTokenRefreshToken, user)) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }

        String accesToken = jwtService.generateAccesToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenService.removeToken(user);
        tokenService.saveToken(refreshToken, user);

        return ResponseEntity.ok(new JwtResponse(accesToken, refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        tokenService.removeToken(user);

        return ResponseEntity.ok("Successfully logged out");
    }
}
