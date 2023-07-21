package com.example.photo_wall_service.controller;

import com.example.photo_wall_service.domain.Request.PhotoRequset;
import com.example.photo_wall_service.result.GlobalResult;
import com.example.photo_wall_service.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/pictrueDO")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @PostMapping("/insertPhotoWall")
    public GlobalResult insertPhotoWall(@RequestBody PhotoRequset photoRequest) throws IOException {
        return pictureService.insertPhotoWall(photoRequest);
    }

    @DeleteMapping("/deletePhotoWall")
    public GlobalResult deletePhotoWall(String uuid) {
        return pictureService.deletePhotoWall(uuid);
    }

    @PutMapping("/updatePhotoWall")
    public GlobalResult updatePhotoWall(@RequestBody PhotoRequset photoRequest) {
        return pictureService.updatePhotoWall(photoRequest);
    }

    @GetMapping("/photoWallList")
    public GlobalResult photoWallList(Integer pageNum, Integer pageSize, String name, Integer world, String author) {
        return pictureService.photoWallList(pageNum, pageSize, name, world, author);
    }

    @GetMapping("/photoWall")
    public GlobalResult photoWall(String uuid) {
        return pictureService.photoWall(uuid);
    }
}
