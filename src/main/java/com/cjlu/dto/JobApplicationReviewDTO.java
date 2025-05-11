package com.cjlu.dto;

import lombok.Data;

/**
 * 岗位申请审核DTO
 */
@Data
public class JobApplicationReviewDTO {
    private String status;  // 审核结果: approved(同意) 或 rejected(拒绝)
}