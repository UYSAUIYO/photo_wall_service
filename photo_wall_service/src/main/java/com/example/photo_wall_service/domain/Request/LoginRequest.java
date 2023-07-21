package com.example.photo_wall_service.domain.Request;

import lombok.Data;

@Data
public class LoginRequest {

    private String account;

    private String password;
}
