package com.cjlu.dto;

import lombok.Data;

/**
 * 资金发放记录DTO
 */
@Data
public class FundingSummaryRecordDTO {
    private Integer studentId;  // 学生ID
    private String name;        // 姓名
    private String college;     // 学院
    private String number;      // 学号
    private Integer totalHours; // 总工时
    private Integer rate;       // 每小时工资（单位：元）
    private Integer totalPay;   // 总工资（单位：元）
    private String month;       // 统计月份（YYYY-MM）
}