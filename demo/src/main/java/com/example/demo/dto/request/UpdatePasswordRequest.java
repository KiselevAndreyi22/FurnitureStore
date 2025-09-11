package com.example.demo.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String code;
    private String password;
}
