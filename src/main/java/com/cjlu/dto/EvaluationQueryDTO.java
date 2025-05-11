package com.cjlu.dto;

import lombok.Data;

/**
 * 评价查询DTO - 使用单月份查询
 */
@Data
public class EvaluationQueryDTO {
    private Integer page;     // 页码
    private Integer pageSize; // 每页记录数
    private String student;   // 学生姓名或学号
    private String teacher;   // 教师姓名
    private String month;     // 评价月份 (YYYY-M)

    // 提供默认值
    public Integer getPage() {
        return page == null || page < 1 ? 1 : page;
    }

    public Integer getPageSize() {
        return pageSize == null || pageSize < 1 ? 10 : pageSize;
    }
}