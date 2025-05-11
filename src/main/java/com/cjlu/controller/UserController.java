package com.cjlu.controller;

import com.cjlu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, UserService userService1) {
        this.userService = userService1;
    }

    /**
     * 获取指定用户ID的用户详情信息。
     * @param userId 用户ID，从URL路径中提取。
     * @return ResponseEntity<?> 返回用户详情信息的HTTP响应。
     *         - 如果成功获取用户详情，返回200 OK，响应体包含用户详情。
     *         - 如果用户不存在，返回404 Not Found。
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDetail(@PathVariable Integer userId) {
        try {
            Object detail = userService.getUserDetail(userId); // 传userId
            return ResponseEntity.ok(detail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}