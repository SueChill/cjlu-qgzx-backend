package com.cjlu.dto;

import lombok.Data;

/**
 * 资金发放导出请求DTO
 */
@Data
public class FundingExportRequestDTO {
    private String month;  // 统计月份（格式：YYYY-MM）
}