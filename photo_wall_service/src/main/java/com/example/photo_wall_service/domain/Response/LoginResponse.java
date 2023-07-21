package com.example.photo_wall_service.domain.Response;

import lombok.Data;

@Data
public class LoginResponse extends UserListResponse {

    private String token;

}
