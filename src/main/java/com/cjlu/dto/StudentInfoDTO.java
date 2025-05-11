package com.cjlu.dto;

import lombok.Data;

/**
 * 学生信息数据传输对象
 * 作用：用于在数据库和服务层之间传递学生信息数据
 *
 * DTO就是数据传输对象，简单理解：
 * 它是一个装数据的容器，用来在系统不同部分之间传递数据
 * 就像超市购物用的购物袋，可以装各种商品一起带走
 */
@Data
public class StudentInfoDTO {

    private Integer userId;        // 用户ID
    private String name;           // 姓名
    private String college;        // 学院
    private String number;         // 学号
    private String phone;          // 手机号
    private Integer gender;        // 性别(0男,1女)
    private String className;      // 班级
    private String level;          // 计算机水平
    private String skill;          // 特长
    private String situation;      // 个人情况
    private Integer difficultStudent; // 是否困难生(0是,1否)
    private Integer loan;          // 是否贷款(0是,1否)
    private Integer jobId;         // 岗位ID
    private String jobTitle;       // 岗位名称
}