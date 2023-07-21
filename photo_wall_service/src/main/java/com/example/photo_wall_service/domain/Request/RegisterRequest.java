package com.example.photo_wall_service.domain.Request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequest implements Serializable {
    private String account;

    private String password;

    private String checkPassword;
}
