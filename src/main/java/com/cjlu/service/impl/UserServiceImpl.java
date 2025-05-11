package com.cjlu.service.impl;

import com.cjlu.entity.User;
import com.cjlu.mapper.UserMapper;
import com.cjlu.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public Object getUserDetail(Integer userId) {
        User user = userMapper.findByIdWithDetail(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return user.getDetailInfo();
    }
}