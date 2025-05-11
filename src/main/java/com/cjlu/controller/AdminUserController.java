package com.cjlu.controller;

import com.cjlu.dto.TeacherCreateDTO;
import com.cjlu.dto.UserInfoDTO;
import com.cjlu.dto.UserQueryDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

/**
 * 管理员用户Controller
 */
@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 获取用户列表
     */
    @GetMapping("/users")
    public Result getUserList(@RequestBody(required = false) UserQueryDTO queryDTO) {
        try {
            // 防止queryDTO为null
            if (queryDTO == null) {
                queryDTO = new UserQueryDTO();
            }

            // 直接使用Service返回的数据对象
            Map<String, Object> data = adminUserService.getUserList(queryDTO);

            // 使用现有的Result.success(Object)方法
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取用户列表失败: " + e.getMessage());
        }
    }

    /**
     * 创建教师用户
     */
    @PostMapping("/teachers")
    public Result createTeacher(@RequestBody TeacherCreateDTO teacherDTO) {
        try {
            UserInfoDTO createdTeacher = adminUserService.createTeacher(teacherDTO);

            // 由于Result类没有包含同时设置msg和data的方法，
            // 我们可以把成功消息和数据一起封装在一个Map中
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("message", "教师用户创建成功");
            resultData.put("teacher", createdTeacher);

            return Result.success(resultData);
        } catch (IllegalArgumentException e) {
            return Result.error("创建失败: " + e.getMessage());
        } catch (Exception e) {
            return Result.error("创建教师用户失败: " + e.getMessage());
        }
    }
}