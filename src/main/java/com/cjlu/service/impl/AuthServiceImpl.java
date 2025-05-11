package com.cjlu.service.impl;


import com.cjlu.dto.StudentRegisterDTO;
import com.cjlu.entity.Result;
import com.cjlu.entity.User;
import com.cjlu.mapper.UserMapper;
import com.cjlu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result registerStudent(StudentRegisterDTO dto) {
        // 1. 校验学号唯一性
        if (userMapper.existsByNumber(dto.getNumber())) {
            return Result.error("该学号已被注册");
        }

        // 2. 校验手机号唯一性
        if (userMapper.existsByPhone(dto.getPhone())) {
            return Result.error("该手机号已被注册");
        }

        // 3. 创建用户记录
        User user = new User();
        user.setName(dto.getName());
        user.setNumber(dto.getNumber());
        user.setCollege(dto.getCollege());  // 院系
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setRole(User.Role.valueOf("student")); // 固定角色为student

        int result = userMapper.insertUser(user);
        return result > 0 ? Result.success() : Result.error("注册失败");
    }
}
