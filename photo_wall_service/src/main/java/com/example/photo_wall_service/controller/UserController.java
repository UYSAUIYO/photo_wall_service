package com.example.photo_wall_service.controller;

import com.example.photo_wall_service.domain.User;
import com.example.photo_wall_service.result.GlobalResult;
import com.example.photo_wall_service.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userDO")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户列表
     *
     * @param name
     * @param email
     * @param account
     * @return
     */
    @GetMapping("/userlist")
    public GlobalResult UserList(@RequestParam String name, @RequestParam String email, @RequestParam String account) {
        return userService.UserList(name, email, account);
    }

    @PostMapping("/updateuser")
    public GlobalResult UpdateUserInfo(@RequestBody User user) {
        if (user == null) {
            return GlobalResult.errorMsg("你丫妹传参呐!");
        }
        return userService.UpdateUserInfo(user);
    }

    @PostMapping("/updatepassword")
    public GlobalResult UpdatePassword(@RequestParam String account, @RequestParam String password, @RequestParam String newPassword) {
        if (StringUtils.isAnyBlank(account, password, newPassword)) {
            return GlobalResult.errorMsg("你丫传的参数有空值!");
        }
        return userService.UpdatePassword(account, password, newPassword);
    }

}
