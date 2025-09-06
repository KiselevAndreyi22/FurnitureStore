package com.example.demo.controller;
import com.example.demo.dto.response.SuccessResponse;
import com.example.demo.dto.response.UserDto;
import com.example.demo.model.User;
import com.example.demo.security.SecurityUtil;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new UserDto(user));
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> updateAvatarUrl(@RequestParam("file") MultipartFile avatarUrlRequest) {
        userService.updateAvatar(SecurityUtil.getCurrentUser().getId(), avatarUrlRequest);

        return ResponseEntity.ok(new SuccessResponse(
                "Аватар обновлен",
                HttpStatus.OK
        ));
    }
}