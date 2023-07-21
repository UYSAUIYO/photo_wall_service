package com.example.photo_wall_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photo_wall_service.constant.RedisConstant;
import com.example.photo_wall_service.domain.Response.LoginResponse;
import com.example.photo_wall_service.domain.Response.UserListResponse;
import com.example.photo_wall_service.domain.User;
import com.example.photo_wall_service.mapper.UserMapper;
import com.example.photo_wall_service.result.GlobalResult;
import com.example.photo_wall_service.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@CrossOrigin
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private static final String SALT = "ZhY_Dan";//盐值，混淆密码。

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 注册
     *
     * @param account
     * @param password
     * @param checkPassword
     * @return
     */
    @Override
    public GlobalResult Register (String account, String password, String checkPassword) {
        if (StringUtils.isAnyBlank (account, password, checkPassword)) {
            return GlobalResult.errorMsg ("你丫传的参数有空值!");
        }
        if (account.length () > 12 || account.length () < 4) {
            return GlobalResult.errorMsg ("账号长度应该介于4和12位之间");
        }
        if (!password.equals (checkPassword)) {
            return GlobalResult.errorMsg ("两次输入的密码不相同");
        } else {
            String validPattern = "[^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,18}$\n]";
            Matcher matcher = Pattern.compile (validPattern).matcher (password);
            if (!matcher.find ()) {
                return GlobalResult.errorMsg ("密码长度应在8-18位之间且必须包含大小写字母和数字");
            }
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<> ();
        lambdaQueryWrapper.eq (User::getAccount, account);
        long count = userMapper.selectCount (lambdaQueryWrapper);
        if (count > 0) {
            return GlobalResult.errorMsg ("当前账号已存在，不能重复!");
        }

        String encryptPassword = DigestUtils.md5DigestAsHex ((SALT + password).getBytes ());

        User user = new User ();
        user.setAccount (account);
        user.setPassword (encryptPassword);
        boolean saveResult = this.save (user);
        if (!saveResult) {
            return GlobalResult.errorMsg ("注册失败!");
        }
        return GlobalResult.ok (user.getId (), "注册成功!");
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public GlobalResult Login (String account, String password) {
        if (StringUtils.isAnyBlank (account, password)) {
            return GlobalResult.errorMsg ("你丫传的参数有空值!");
        }
        if (account.length () > 12 || account.length () < 4) {
            return GlobalResult.errorMsg ("账号长度应该介于4和12位之间");
        }
        String validPattern = "[^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,18}$\n]";
        Matcher matcher = Pattern.compile (validPattern).matcher (password);
        if (!matcher.find ()) {
            return GlobalResult.errorMsg ("密码长度应在8-18位之间且必须包含大小写字母和数字");
        }
        String encryptPassword = DigestUtils.md5DigestAsHex ((SALT + password).getBytes ());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<> ();
        lambdaQueryWrapper.eq (User::getAccount, account)
                .eq (User::getPassword, encryptPassword);
        User user = userMapper.selectOne (lambdaQueryWrapper);
        //用户不存在
        if (user == null) {
            return GlobalResult.errorMsg ("账号输错了或密码不正确");
        }
        JwtBuilder builder = Jwts.builder ();


        String token = builder.setSubject (account)                     //载荷部分，主题，就是token中携带的数据，这里把用户名放进去
                .setIssuedAt (new Date ())                            //设置token的生成时间
                .setId (user.getId () + "")               //设置用户id为token  id      ''是因为用户id是int类型，需要转换为字符串类型
               //map中可以存放用户的角色权限信息
                .setExpiration (new Date (System.currentTimeMillis () + 24 * 60 * 60 * 1000)) //设置token过期时间，当前时间加一天就是时效为一天过期
                .signWith (SignatureAlgorithm.HS256, "ETC_ZhY_Dan")     //签名部分，设置HS256加密方式和加密密码,ETC_ZhY_Dan是自定义的密码
                .compact ();
        //设置token到redis
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue ().set (String.format (RedisConstant.TOKEN_PREFIX, token), user.getAccount (), expire, TimeUnit.SECONDS);

        //封装信息，返回前端
        LoginResponse loginResponse = new LoginResponse ();
        loginResponse.setAccount (user.getAccount ());
        loginResponse.setEmail (user.getEmail ());
        loginResponse.setAvatarUrl (user.getAvatarUrl ());
        loginResponse.setName (user.getName ());
        loginResponse.setId (user.getId ());
        loginResponse.setSex (user.getSex ());
        loginResponse.setToken (token);

        return GlobalResult.ok (loginResponse, "登录成功");
    }

    /**
     * 用户列表
     *
     * @param name
     * @param email
     * @param account
     * @return
     */
    @Override
    public GlobalResult UserList (String name, String email, String account) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<> ();
        if (name != null && !name.isEmpty ()) {
            lambdaQueryWrapper.like (User::getName, name);
        }
        if (email != null && !email.isEmpty ()) {
            lambdaQueryWrapper.like (User::getEmail, email);
        }
        if (account != null && !account.isEmpty ()) {
            lambdaQueryWrapper.like (User::getAccount, account);
        }
        List<User> users = userMapper.selectList (lambdaQueryWrapper);
        List<UserListResponse> list = new ArrayList<> ();
        for (User user : users) {
            list.add (getSafetyUser (user));//关键信息脱敏
        }
        return GlobalResult.ok (list);
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @Override
    public GlobalResult UpdateUserInfo (User user) {
        userMapper.updateById (user);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<> ();
        lambdaQueryWrapper.eq (User::getId, user.getId ());
        User user1 = userMapper.selectOne (lambdaQueryWrapper);
        return GlobalResult.ok (getSafetyUser (user1), "修改成功");
    }


    @Override
    public GlobalResult UpdatePassword (String account, String password, String newPassword) {
        if (StringUtils.isAnyBlank (account, password, newPassword)) {
            return GlobalResult.errorMsg ("你丫传的参数有空值!");
        }
        if (password.equals (newPassword)) {
            return GlobalResult.errorMsg ("新密码与原密码不能相同!");
        }
        String validPattern = "[^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,18}$\n]";
        Matcher matcher = Pattern.compile (validPattern).matcher (newPassword);
        if (!matcher.find ()) {
            return GlobalResult.errorMsg ("密码长度应在8~18位之间且必须包含大小写字母和数字");
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<> ();
        lambdaQueryWrapper.eq (User::getAccount, account);
        User user = userMapper.selectOne (lambdaQueryWrapper);
        String encryptPassword = DigestUtils.md5DigestAsHex ((SALT + password).getBytes ());
        if (!(user.getPassword ()).equals (encryptPassword)) {
            return GlobalResult.errorMsg ("原密码错误");
        }
        String encryptNewPassword = DigestUtils.md5DigestAsHex ((SALT + newPassword).getBytes ());
        if (userMapper.UpdatePassword (account, encryptNewPassword)) {
            return GlobalResult.ok ("修改成功！");
        }
        return GlobalResult.errorMsg ("修改失败");
    }

    /**
     * 用户信息脱敏
     *
     * @param originUser
     * @return
     */
    @Override
    public UserListResponse getSafetyUser (User originUser) {
        if (originUser == null) {
            return null;
        }
        UserListResponse safetyUser = new UserListResponse ();
        safetyUser.setId (originUser.getId ());
        safetyUser.setName (originUser.getName ());
        safetyUser.setAccount (originUser.getAccount ());
        safetyUser.setAvatarUrl (originUser.getAvatarUrl ());
        safetyUser.setSex (originUser.getSex ());
        safetyUser.setEmail (originUser.getEmail ());
        return safetyUser;
    }
}


