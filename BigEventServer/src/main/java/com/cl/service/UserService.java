package com.cl.service;

import com.cl.pojo.User;

public interface UserService {

    User findByUserName(String username);

    void register(String username, String password);

    Boolean login(String username, String password);
}
