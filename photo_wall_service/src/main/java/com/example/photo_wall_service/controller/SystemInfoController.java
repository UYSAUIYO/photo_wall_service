package com.example.photo_wall_service.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import static com.example.photo_wall_service.util.SystemInfoUtil.getInfo;

/**
 * @author YuWen
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/system")
public class SystemInfoController {
    @GetMapping("/info")
    public JSONObject ComputerInfo () throws ExecutionException, InterruptedException {
        try {
            return getInfo ();
        } catch (UnknownHostException e) {
            throw new RuntimeException (e);
        }
    }
}
