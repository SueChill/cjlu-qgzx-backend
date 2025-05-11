package com.cjlu.dto;

import lombok.Data;

/**
 * 学生评价DTO
 */
@Data
public class StudentEvaluationDTO {
    private Integer studentId;  // 已修改为Integer类型
    private String month;       // 评价月份 YYYY-MM
    private String comment;     // 评价内容
}