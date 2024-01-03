package com.cl.controller;

import com.cl.pojo.Result;
import com.cl.pojo.User;
import com.cl.service.UserService;
import com.cl.utils.JwtUtil;
import com.cl.utils.Md5Util;
import com.cl.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User user = userService.findByUserName(username);
        if (user==null){
            userService.register(username,password);
            return Result.success();
        }else {
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User user = userService.findByUserName(username);
        if (user==null){
            return Result.error("用户名不存在！");
        }

        if (Md5Util.getMD5String(password).equals(user.getPassword())){
            Map<String ,Object> claims = new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误！");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
        //根据用户名查找用户
        //Map<String, Object> userMap = JwtUtil.parseToken(token);
        //String username = (String) userMap.get("username");
        Map<String, Object> userMap = ThreadLocalUtil.get();
        User user = userService.findByUserName((String) userMap.get("username"));
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success("用户信息修改成功");
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success("头像修改成功");
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params){
        //1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("请输入密码！");
        }
        //原密码是否一致 先根据用户名查找密码，再比对
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码有误！");
        }
        //两次新密码是否一致
        if (!newPwd.equals(rePwd)){
            return Result.error("两次输入的密码不一致！");
        }
        userService.updatePwd(newPwd);
        return Result.success("密码修改成功！");
    }
}
