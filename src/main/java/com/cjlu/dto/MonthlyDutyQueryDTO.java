package com.cjlu.dto;

import lombok.Data;

/**
 * 月度值班查询DTO
 */
@Data
public class MonthlyDutyQueryDTO {
    private String month; // 查询月份，格式为YYYY-MM
}