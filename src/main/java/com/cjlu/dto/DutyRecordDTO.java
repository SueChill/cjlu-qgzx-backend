package com.cjlu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.List;

/**
 * 值班记录数据传输对象
 */
@Data
public class DutyRecordDTO {
    private String date;           // 值班日期
    private List<String> time;     // 值班时间段列表
    private String status;      // 状态：出勤、请假
    private String reason;      // 请假原因（仅请假记录有）

    @JsonIgnore
    // 用于MyBatis查询结果的临时字段
    private String timeSlot;       // 单个时间段，用于SQL查询结果映射
}