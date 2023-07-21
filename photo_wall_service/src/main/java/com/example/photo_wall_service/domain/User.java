package com.example.photo_wall_service.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName user
 */
@Data
@TableName("user")
public class User implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String account;

    /**
     *
     */
    private String password;

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

    private static final long serialVersionUID = 1L;
}