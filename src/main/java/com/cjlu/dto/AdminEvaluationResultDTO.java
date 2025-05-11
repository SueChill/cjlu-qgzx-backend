package com.cjlu.dto;

import lombok.Data;
import java.util.Date;

/**
 * 管理端学生评价结果DTO
 */
@Data
public class AdminEvaluationResultDTO {
    private Integer id;            // 评价ID (对应evaluation_id)
    private Integer studentId;     // 学生ID
    private String studentName;    // 学生姓名
    private String studentNumber;  // 学号
    private Integer teacherId;     // 教师ID
    private String teacherName;    // 教师姓名
    private Integer jobId;         // 岗位ID
    private String jobTitle;       // 岗位名称
    private String month;          // 评价月份 (YYYY-MM)
    private String comment;        // 评价内容
    private Date evaluationTime;   // 评价时间 (新增字段)
}