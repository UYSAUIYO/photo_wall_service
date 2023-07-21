package com.example.photo_wall_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.photo_wall_service.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author HUAWEI
 * @description 针对表【comment】的数据库操作Mapper
 * @createDate 2023-03-10 16:48:20
 * @Entity generator.domain.Comment
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    boolean deleteByUuid(String uuid);

    boolean deleteByPhotoId(String photoId);


}
