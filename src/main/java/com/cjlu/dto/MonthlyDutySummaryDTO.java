package com.cjlu.dto;

import lombok.Data;
import java.util.List;

/**
 * 月度值班情况汇总DTO
 */
@Data
public class MonthlyDutySummaryDTO {
    // 内部类DutyRecord，用于表示值班记录
    @Data
    public static class DutyRecord {
        private String date;        // 日期，格式为YYYY-MM-DD
        private List<String> time;  // 时间段列表，如["09:00-10:30", "14:00-16:00"]
        private String status;      // 状态：出勤、请假
        private String reason;      // 请假原因（仅请假记录有）
    }

    private List<DutyRecord> dutyRecords;  // 值班记录列表
    private Float totalHours;              // 总工时
    private Integer salary;                // 估算工资
}