package com.example.photo_wall_service.domain.Request;

import com.example.photo_wall_service.domain.PhotoWall;
import lombok.Data;


@Data
public class PhotoRequset extends PhotoWall {
    private String[] photoUrls;
}
