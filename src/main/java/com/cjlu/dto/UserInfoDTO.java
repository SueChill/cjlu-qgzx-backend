package com.cjlu.dto;

import lombok.Data;

/**
 * 用户信息DTO
 */
@Data
public class UserInfoDTO {
    private Integer id;      // 用户ID
    private String name;     // 姓名
    private String college;  // 院系
    private String number;   // 工号/学号
    private String phone;    // 手机号
    private String role;     // 用户角色
}