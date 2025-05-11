package com.cjlu.dto;

import lombok.Data;

/**
 * 教师创建DTO
 */
@Data
public class TeacherCreateDTO {
    private Integer id;      // 添加ID字段，用于接收数据库自增ID
    private String name;     // 姓名
    private String college;  // 院系
    private String number;   // 工号
    private String phone;    // 手机号
    private String password; // 密码
}