package com.cjlu.dto;

import lombok.Data;
import java.util.List;

/**
 * 工时审核DTO
 */
@Data
public class WorkHourReviewDTO {
    private List<ReviewItem> reviews;

    @Data
    public static class ReviewItem {
        private String recordId;  // 工时记录ID
        private String status;    // 审核结果(approved/rejected)
    }
}