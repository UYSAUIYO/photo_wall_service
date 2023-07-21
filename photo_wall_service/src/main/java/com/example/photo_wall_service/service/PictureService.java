package com.example.photo_wall_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photo_wall_service.domain.PhotoWall;
import com.example.photo_wall_service.domain.Request.PhotoRequset;
import com.example.photo_wall_service.result.GlobalResult;

import java.io.IOException;


public interface PictureService extends IService<PhotoWall> {

    GlobalResult insertPhotoWall (PhotoRequset photoRequest) throws IOException;

    GlobalResult deletePhotoWall (String uuid);

    GlobalResult updatePhotoWall (PhotoRequset photoRequest);

    GlobalResult photoWallList (Integer pageNum, Integer pageSize, String name, Integer world, String author);

    GlobalResult photoWall (String uuid);

}
