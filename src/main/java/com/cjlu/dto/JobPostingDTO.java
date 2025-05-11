package com.cjlu.dto;

import lombok.Data;

/**
 * 岗位信息DTO
 */
@Data
public class JobPostingDTO {
    private Integer id;           // 岗位ID
    private String jobTitle;      // 岗位名称
    private String requirements;  // 工作要求
    private String time;          // 工作时间
    private String location;      // 工作地点
    private String phone;         // 联系方式
    private String publisher;     // 发布者姓名
    private String publishDate;   // 发布日期
}