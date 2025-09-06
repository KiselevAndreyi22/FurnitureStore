package com.example.demo.dto.response;

import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String email;
    private String avatarUrl;

    public UserDto() {}

    public UserDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatarUrl = user.getAvatarUrl();
    }
}
