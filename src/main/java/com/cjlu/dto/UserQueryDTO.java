package com.cjlu.dto;

import lombok.Data;

/**
 * 用户查询DTO
 */
@Data
public class UserQueryDTO {
    private Integer page;     // 页码
    private Integer pageSize; // 每页记录数
    private String role;      // 角色
    private String keyword;   // 关键字（姓名、学号等）

    // 提供默认值
    public Integer getPage() {
        return page == null || page < 1 ? 1 : page;
    }

    public Integer getPageSize() {
        return pageSize == null || pageSize < 1 ? 10 : pageSize;
    }
}