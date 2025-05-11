package com.cjlu.controller;

import com.cjlu.dto.WorkHourQueryDTO;
import com.cjlu.dto.WorkHourReviewDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.WorkHourReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 工时审核Controller
 */
@RestController
@RequestMapping("/api/teacher/work-hours")
public class WorkHourReviewController {

    @Autowired
    private WorkHourReviewService workHourReviewService;

    /**
     * 获取待审核工时记录
     */
    @GetMapping("/pending")
    public Result getPendingWorkHours(
            @RequestBody(required = false) WorkHourQueryDTO queryDTO,
            @RequestParam Integer teacherId) {
        try {
            // 防止queryDTO为null
            if (queryDTO == null) {
                queryDTO = new WorkHourQueryDTO();
            }

            Map<String, Object> data = workHourReviewService.getPendingWorkHours(queryDTO, teacherId);

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取待审核工时记录失败: " + e.getMessage());
        }
    }

    /**
     * 审核工时记录
     */
    @PostMapping("/review")
    public Result reviewWorkHours(
            @RequestBody WorkHourReviewDTO reviewDTO,
            @RequestParam Integer teacherId) {
        try {
            boolean success = workHourReviewService.reviewWorkHours(reviewDTO, teacherId);

            if (success) {
                return Result.success("审核成功");
            } else {
                return Result.error("部分工时记录审核失败");
            }
        } catch (IllegalArgumentException e) {
            return Result.error("审核失败: " + e.getMessage());
        } catch (RuntimeException e) {
            return Result.error("审核失败: " + e.getMessage());
        } catch (Exception e) {
            return Result.error("审核失败: 系统错误");
        }
    }
}