package com.example.photo_wall_service.domain.Response;

import lombok.Data;

@Data
public class UserListResponse {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String account;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     *
     */
    private String name;

    /**
     * 性别：0男1女
     */
    private Integer sex;

    /**
     *
     */
    private String email;
}
