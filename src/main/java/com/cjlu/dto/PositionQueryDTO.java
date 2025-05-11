package com.cjlu.dto;

import lombok.Data;

@Data
public class PositionQueryDTO {
    private Integer page;     // 页码
    private Integer pageSize; // 每页记录数
    private String keyword;   // 关键字（岗位名称、教师姓名等）

    // 提供默认值
    public Integer getPage() {
        return page == null || page < 1 ? 1 : page;
    }

    public Integer getPageSize() {
        return pageSize == null || pageSize < 1 ? 10 : pageSize;
    }
}