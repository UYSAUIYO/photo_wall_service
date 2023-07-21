package com.example.photo_wall_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.photo_wall_service.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    boolean UpdatePassword (String account, String newPassword);
}
