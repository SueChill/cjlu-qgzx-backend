package com.cjlu.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 岗位信息实体
 */
@Data
public class JobPosting {
    private Integer id;             // 岗位ID
    private String title;           // 岗位名称
    private String description;     // 工作要求和联系方式
    private String workTime;        // 工作时间
    private String location;        // 工作地点
    private Integer publisherId;    // 发布者ID (教师ID)
    private LocalDateTime createdAt; // 创建时间
}