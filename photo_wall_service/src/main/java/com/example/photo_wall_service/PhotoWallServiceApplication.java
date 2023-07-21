package com.example.photo_wall_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YuWen
 */
@SpringBootApplication
@MapperScan("com.example.photo_wall_service.mapper")
public class PhotoWallServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoWallServiceApplication.class, args);
    }

}
