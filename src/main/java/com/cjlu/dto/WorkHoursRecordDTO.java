package com.cjlu.dto;

import lombok.Data;

/**
 * 工时记录DTO
 */
@Data
public class WorkHoursRecordDTO {
    private Integer id;        // 记录ID
    private Integer studentId; // 学生ID
    private String name;       // 学生姓名
    private String number;     // 学号
    private String date;       // 日期
    private Float hours;       // 工时
}