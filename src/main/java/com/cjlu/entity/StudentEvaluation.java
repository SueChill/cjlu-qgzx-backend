package com.cjlu.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学生考核评价实体
 */
@Data
public class StudentEvaluation {
    private Integer evaluationId;    // 评价ID
    private Integer studentId;       // 学生ID
    private Integer teacherId;       // 教师ID
    private Integer jobId;           // 岗位ID
    private String month;            // 评价月份，格式为YYYY-MM
    private String comment;          // 评价内容
    private LocalDateTime evaluationTime; // 评价时间
}