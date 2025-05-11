package com.cjlu.dto;

import lombok.Data;

/**
 * 工时查询DTO
 */
@Data
public class WorkHourQueryDTO {
    private String month;    // 查询月份，格式为YYYY-MM
    private Integer page;    // 页码
    private Integer pageSize; // 每页记录数

    // 设置默认值
    public Integer getPage() {
        return page == null || page < 1 ? 1 : page;
    }

    public Integer getPageSize() {
        return pageSize == null || pageSize < 1 ? 10 : pageSize;
    }
}