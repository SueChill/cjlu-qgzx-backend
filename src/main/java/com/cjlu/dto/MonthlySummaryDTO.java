package com.cjlu.dto;

import lombok.Data;

/**
 * 月度工时与工资摘要DTO
 */

@Data
public class MonthlySummaryDTO {
    private Integer studentId;  // 学生ID
    private String month;       // 月份，格式：YYYY-MM
    private Double totalHours;  // 总工时
    private Integer salary;     // 工资（工时 × 24元）
}