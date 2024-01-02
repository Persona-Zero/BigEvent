package com.cl.service.impl;

import com.cl.mapper.UserMapper;
import com.cl.pojo.User;
import com.cl.service.UserService;
import com.cl.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User user = userMapper.findByUserName(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);
        //添加
        userMapper.addUser(username,md5String);
    }

    @Override
    public Boolean login(String username, String password) {

        return null;
    }
}
