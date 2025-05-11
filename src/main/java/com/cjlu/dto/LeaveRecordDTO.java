package com.cjlu.dto;

import lombok.Data;
import java.util.List;

/**
 * 请假记录数据传输对象
 */
@Data
public class LeaveRecordDTO {
    private String date;           // 请假日期
    private List<String> time;     // 请假时间段列表
    private String reason;         // 请假理由
}