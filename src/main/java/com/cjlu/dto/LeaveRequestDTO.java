package com.cjlu.dto;

import lombok.Data;
import java.util.List;

/**
 * 学生请假申请数据传输对象
 */
@Data
public class LeaveRequestDTO {
    private Integer studentId;  // 学生ID
    private Integer jobId;      // 岗位ID（可选，如不提供则自动查询）
    private String date;        // 请假日期，格式为YYYY-MM-DD
    private List<String> time;  // 请假时间段数组，如["09:00-09:30", "10:00-10:30"]
    private String reason;      // 请假理由
}