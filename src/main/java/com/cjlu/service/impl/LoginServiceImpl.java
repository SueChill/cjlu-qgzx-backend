package com.cjlu.service.impl;

import com.cjlu.entity.User;
import com.cjlu.mapper.UserMapper;
import com.cjlu.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private final UserMapper userMapper;

    @Autowired
    public LoginServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User login(String number, String password) {
        return userMapper.findByNumberAndPassword(number, password);
    }
}