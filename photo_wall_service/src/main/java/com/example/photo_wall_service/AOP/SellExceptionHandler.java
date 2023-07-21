package com.example.photo_wall_service.AOP;

import com.example.photo_wall_service.Exception.UserException;
import com.example.photo_wall_service.result.GlobalResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SellExceptionHandler {

    //拦截登陆异常
    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public GlobalResult handleLoginExcpetion () {
        return GlobalResult.errorMsg ("未登录或登录时间过期");
    }
}

