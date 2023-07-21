package com.example.photo_wall_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photo_wall_service.domain.Response.UserListResponse;
import com.example.photo_wall_service.domain.User;
import com.example.photo_wall_service.result.GlobalResult;

/**
 * @author HUAWEI
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-07-18 13:01:09
 */
public interface UserService extends IService<User> {
    GlobalResult Register(String account, String password, String checkPassword);

    GlobalResult Login(String account, String password);

    GlobalResult UserList(String name, String email, String account);

    GlobalResult UpdateUserInfo(User user);

    GlobalResult UpdatePassword(String account, String password, String newPassword);


    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    UserListResponse getSafetyUser(User originUser);


}
