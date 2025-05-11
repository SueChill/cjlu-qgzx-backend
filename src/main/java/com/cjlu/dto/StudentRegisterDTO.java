package com.cjlu.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 学生注册dto
 */
@Data
public class StudentRegisterDTO {
    @NotBlank(message = "姓名不能为空")
    @Size(min = 2, max = 10, message = "姓名长度2-10位")
    private String name;

    @NotBlank(message = "学号不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]{6,20}$", message = "学号格式不正确")
    private String number;

    @NotBlank(message = "院系不能为空")
    @Size(max = 50, message = "院系名称过长")
    private String college;  // 新增字段

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    private String password;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
}

