package com.cjlu.controller;

import com.cjlu.dto.JobPostingDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 岗位发布Controller
 */
@RestController
@RequestMapping("/api/teacher")
public class JobPostingController {

    @Autowired
    private JobPostingService jobPostingService;

    /**
     * 发布勤工助学岗位
     */
    @PostMapping("/students")
    public Result publishJob(@RequestBody JobPostingDTO jobPostingDTO, @RequestParam Integer teacherId) {
        try {
            boolean success = jobPostingService.publishJob(jobPostingDTO, teacherId);
            if (success) {
                return Result.success(null);
            } else {
                return Result.error("发布岗位失败");
            }
        } catch (IllegalArgumentException e) {
            return Result.error("发布岗位失败: " + e.getMessage());
        } catch (Exception e) {
            return Result.error("发布岗位失败: " + e.getMessage());
        }
    }
}