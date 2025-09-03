package com.example.demo.dto.response;

import com.example.demo.model.User;

public class UserDto {
    private String username;
    private String email;

    public UserDto() {}

    public UserDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
