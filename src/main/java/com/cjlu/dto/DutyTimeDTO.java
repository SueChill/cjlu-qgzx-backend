package com.cjlu.dto;

import lombok.Data;
import java.util.List;

/**
 * 学生提交值班时间的数据传输对象
 */
@Data
public class DutyTimeDTO {
    private Integer studentId;  // 学生ID
    private Integer jobId;      // 岗位ID（可选，如不提供则自动查询）
    private String date;        // 值班日期，格式为YYYY-MM-DD
    private List<String> time;  // 值班时间段数组，如["09:00-10:30", "14:00-16:00"]
}