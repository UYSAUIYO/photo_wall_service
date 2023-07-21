package com.example.photo_wall_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photo_wall_service.domain.PhotoWall;
import com.example.photo_wall_service.domain.Request.PhotoRequset;
import com.example.photo_wall_service.mapper.PictureMapper;
import com.example.photo_wall_service.result.GlobalResult;
import com.example.photo_wall_service.service.PictureService;
import com.fasterxml.uuid.Generators;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

import static com.example.photo_wall_service.config.UploadConfig.SERVICE_HEAD;
import static com.example.photo_wall_service.config.UploadConfig.STORAGE_ADDRESS;
import static com.example.photo_wall_service.util.ArrayToJson.ArrayToJson;
import static com.example.photo_wall_service.util.ThumbnailPcitrue.Thumbnail;

@AllArgsConstructor
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, PhotoWall> implements PictureService {

    private PictureMapper pictureMapper;

    @Override
    public GlobalResult insertPhotoWall(PhotoRequset photoRequest) throws IOException {
        Long t1, t2;
        t1 = System.nanoTime();
        //生成图片url的json字段
        String photoUrls = ArrayToJson(photoRequest.getPhotoUrls());

        UUID uuid = Generators.timeBasedGenerator().generate();//uuid

        //压缩第一张图片
        String firstUrl = STORAGE_ADDRESS + photoRequest.getPhotoUrls()[0];
        String thumbnail = Thumbnail(firstUrl);

        PhotoWall photoWall = new PhotoWall();
        BeanUtils.copyProperties(photoRequest, photoWall);//复制

        photoWall.setThumbnail(SERVICE_HEAD + "/img/" + thumbnail);//预览图地址
        photoWall.setUuid(String.valueOf(uuid));//版本1uuid
        photoWall.setPhotoUrl(photoUrls);//图片地址集合

        if (this.baseMapper.insert(photoWall) == 0) {
            return GlobalResult.errorException("新增失败");
        }
        t2 = System.nanoTime();
        System.out.println((t2 - t1) / 1000000 + "毫秒");
        return GlobalResult.ok("新增成功");
    }

    @Override
    public GlobalResult deletePhotoWall(String uuid) {

        if (pictureMapper.deletePhotoWall(uuid)) {
            return GlobalResult.ok("删除成功");
        }
        return GlobalResult.errorMsg("删除失败");
    }

    @Override
    public GlobalResult updatePhotoWall(PhotoRequset photoRequest) {
        if (pictureMapper.updateById(photoRequest) > 0) {
            return GlobalResult.ok("修改成功");
        }
        return GlobalResult.errorMsg("修改失败");
    }

    @Override
    public GlobalResult photoWallList(Integer pageNum, Integer pageSize, String name, Integer world, String author) {
        LambdaQueryWrapper<PhotoWall> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(name)) {
            lambdaQueryWrapper.like(PhotoWall::getName, name);
        }
        if (!StringUtils.isBlank(author)) {
            lambdaQueryWrapper.like(PhotoWall::getAuthor, author);
        }
        if (world != null) {
            lambdaQueryWrapper.eq(PhotoWall::getWorld, world);
        }
        Page<PhotoWall> page = new Page<>(pageSize, pageNum);//分页查询
        page = this.baseMapper.selectPage(page, lambdaQueryWrapper);
        return GlobalResult.ok(page, "查询成功");
    }

    @Override
    public GlobalResult photoWall(String uuid) {
        LambdaQueryWrapper<PhotoWall> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PhotoWall::getUuid, uuid);
        PhotoWall photoWall = pictureMapper.selectOne(lambdaQueryWrapper);
        return GlobalResult.ok(photoWall, "成功");

    }
}
