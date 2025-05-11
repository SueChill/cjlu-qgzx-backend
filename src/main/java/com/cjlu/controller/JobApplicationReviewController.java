package com.cjlu.controller;

import com.cjlu.dto.JobApplicationReviewDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.JobApplicationReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 岗位申请审核Controller
 */
@RestController
@RequestMapping("/api/teacher")
public class JobApplicationReviewController {

    @Autowired
    private JobApplicationReviewService jobApplicationReviewService;

    /**
     * 审核学生的岗位申请
     */
    @PostMapping("/job-applications/{applicationId}/review")
    public Result reviewApplication(
            @PathVariable("applicationId") Integer applicationId,
            @RequestParam Integer teacherId,
            @RequestBody JobApplicationReviewDTO reviewDTO) {
        try {
            System.out.println("接收到审核请求：applicationId=" + applicationId +
                    ", teacherId=" + teacherId +
                    ", status=" + reviewDTO.getStatus());

            boolean success = jobApplicationReviewService.reviewApplication(
                    applicationId,
                    teacherId,
                    reviewDTO.getStatus());

            if (success) {
                return Result.success("审核成功");
            } else {
                return Result.error("审核失败，未能更新申请状态");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("参数错误: " + e.getMessage());
            return Result.error("审核失败: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("运行时错误: " + e.getMessage());
            return Result.error("审核失败: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("系统错误: " + e.getMessage());
            e.printStackTrace();
            return Result.error("审核失败: 系统错误");
        }
    }
}