package com.cjlu.dto;

import lombok.Data;

/**
 * 岗位审核请求DTO
 */
@Data
public class JobPostingReviewDTO {
    private Integer positionId;  // 岗位ID
    private String status;       // 审核状态: approve(同意) / reject(拒绝)
}