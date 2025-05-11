package com.cjlu.dto;

import lombok.Data;

/**
 * 资金发放查询条件DTO
 */
@Data
public class FundingSummaryQueryDTO {
    private Integer page;     // 页码
    private Integer pageSize; // 每页记录数
    private String keyword;   // 关键字（姓名、学号等）
    private String college;   // 学院名称
    private String month;     // 统计月份（格式：YYYY-MM）

    // 提供默认值
    public Integer getPage() {
        return page == null || page < 1 ? 1 : page;
    }

    public Integer getPageSize() {
        return pageSize == null || pageSize < 1 ? 10 : pageSize;
    }
}