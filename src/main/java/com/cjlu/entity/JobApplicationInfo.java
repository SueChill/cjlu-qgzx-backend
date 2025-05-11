package com.cjlu.entity;

import lombok.Data;

/**
 * 岗位申请信息实体
 */
@Data
public class JobApplicationInfo {
    private Integer id;           // 申请ID
    private Integer studentId;    // 学生ID
    private Integer jobId;        // 岗位ID
    private Integer status;       // 申请状态
    private Integer publisherId;  // 发布者(教师)ID
}