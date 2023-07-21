package com.example.photo_wall_service.AOP;

import com.example.photo_wall_service.Exception.UserException;
import com.example.photo_wall_service.constant.RedisConstant;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.example.photo_wall_service.controller.UserController*.*(..))")
    public void verify () {
    }

    @Before("verify()")
    public void doverify () throws UserException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes ();
        HttpServletRequest request = attributes.getRequest ();
        //从http请求头中拿到token
        String token = request.getHeader ("Authorization");
        if (token == null) {
            throw new UserException ("token为空");

        }
        String tokenValue = redisTemplate.opsForValue ().get (String.format (RedisConstant.TOKEN_PREFIX, token));
        if (StringUtils.isEmpty (tokenValue)) {
            throw new UserException ("该token不存在");

        }
        redisTemplate.opsForValue ().set (String.format (RedisConstant.TOKEN_PREFIX, token), tokenValue, RedisConstant.EXPIRE, TimeUnit.SECONDS);
    }
}

