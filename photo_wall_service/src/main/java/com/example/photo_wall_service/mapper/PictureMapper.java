package com.example.photo_wall_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.photo_wall_service.domain.PhotoWall;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PictureMapper extends BaseMapper<PhotoWall> {
    boolean deletePhotoWall (String uuid);
}
