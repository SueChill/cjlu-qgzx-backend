package com.cjlu.controller;

import com.cjlu.dto.JobPostingQueryDTO;
import com.cjlu.dto.JobPostingReviewDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理员岗位审核Controller
 */
@RestController
@RequestMapping("/api/admin/positions")
public class AdminJobPostingController {

    @Autowired
    private JobPostingService jobPostingService;

    /**
     * 获取待审核的岗位列表
     */
    @GetMapping("/pending")
    public Result getPendingPositions(@RequestBody(required = false) JobPostingQueryDTO queryDTO) {
        try {
            if (queryDTO == null) {
                queryDTO = new JobPostingQueryDTO();
            }

            Map<String, Object> data = jobPostingService.getPendingJobPostings(queryDTO);

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取待审核岗位列表失败: " + e.getMessage());
        }
    }

    /**
     * 审核岗位
     */
    @PostMapping("/review")
    public Result reviewPosition(@RequestBody JobPostingReviewDTO reviewDTO) {
        try {
            // 进行岗位审核
            boolean success = jobPostingService.reviewJobPosting(reviewDTO);

            if (success) {
                String action = "approve".equals(reviewDTO.getStatus()) ? "审核通过" : "审核拒绝";
                return Result.success(action + "成功");
            } else {
                return Result.error("审核失败，请检查岗位ID是否存在");
            }
        } catch (Exception e) {
            return Result.error("岗位审核失败: " + e.getMessage());
        }
    }
}