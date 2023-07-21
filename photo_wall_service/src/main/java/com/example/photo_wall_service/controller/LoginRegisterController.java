package com.example.photo_wall_service.controller;

import com.example.photo_wall_service.domain.Request.LoginRequest;
import com.example.photo_wall_service.domain.Request.RegisterRequest;
import com.example.photo_wall_service.result.GlobalResult;
import com.example.photo_wall_service.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//允许跨域
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginRegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public GlobalResult Register (@RequestBody RegisterRequest registerRequest) {
        if (registerRequest == null) {
            return GlobalResult.errorMsg ("你丫妹传参呐!");
        }
        String account = registerRequest.getAccount ();
        String password = registerRequest.getPassword ();
        String checkPassword = registerRequest.getCheckPassword ();
        if (StringUtils.isAnyBlank (account, password, checkPassword)) {
            return GlobalResult.errorMsg ("你丫传的参数有空值!");
        }
        return userService.Register (account, password, checkPassword);
    }

    @PostMapping("/login")
    public GlobalResult Login (@RequestBody LoginRequest loginRequest) {
        if (loginRequest == null) {
            return GlobalResult.errorMsg ("你丫妹传参呐!");
        }
        String account = loginRequest.getAccount ();
        String password = loginRequest.getPassword ();
        if (StringUtils.isAnyBlank (account, password)) {
            return GlobalResult.errorMsg ("你丫传的参数有空值!");
        }
        return userService.Login (account, password);
    }
}
