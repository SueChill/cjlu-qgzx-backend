package com.cjlu.dto;

import lombok.Data;

/**
 * 月度值班情况查询参数
 */
@Data
public class MonthlySummaryQueryDTO {
    private Integer studentId;  // 学生ID
    private String month;       // 查询月份，格式为YYYY-MM
}