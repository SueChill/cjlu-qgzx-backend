package com.cjlu.controller;

import com.cjlu.dto.StudentRegisterDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生账户注册控制器
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/student/register")
    public Result registerStudent(@Valid @RequestBody StudentRegisterDTO dto) {
        return authService.registerStudent(dto);
    }
}
