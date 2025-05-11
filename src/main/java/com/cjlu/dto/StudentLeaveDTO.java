package com.cjlu.dto;

import lombok.Data;
import java.util.List;

/**
 * 学生请假申请数据传输对象
 * 扩展自LeaveRecordDTO，增加studentId字段用于内部处理
 */
@Data
public class StudentLeaveDTO {
    private Integer studentId;     // 学生ID（内部使用，可选）
    private String date;           // 请假日期，格式为 "YYYY-MM-DD"
    private List<String> time;     // 请假时间段数组，如 ["09:00-09:30", "10:00-10:30"]
    private String reason;         // 请假理由
}