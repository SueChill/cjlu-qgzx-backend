package com.cjlu.dto;

import lombok.Data;
import java.util.List;

/**
 * 工时记录响应DTO
 */
@Data
public class WorkHourRecordDTO {
    private String recordId;      // 工时记录ID
    private String studentId;     // 学生ID
    private String studentName;   // 学生姓名
    private String date;          // 值班日期
    private Object timeSlots;     // 值班时间段 - 可以是String或List<String>
    private String status;        // 审核状态
    private float totalHours;     // 值班总时长
}