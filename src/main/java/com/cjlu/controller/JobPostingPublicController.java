package com.cjlu.controller;

import com.cjlu.dto.JobPostingQueryDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 岗位公示Controller - 面向学生和公众的岗位信息
 */
@RestController
@RequestMapping("/api/positions")
public class JobPostingPublicController {

    @Autowired
    private JobPostingService jobPostingService;

    /**
     * 获取已审核通过的岗位列表（岗位公示）
     */
    @GetMapping("/approved")
    public Result getApprovedPositions(@RequestBody(required = false) JobPostingQueryDTO queryDTO) {
        try {
            if (queryDTO == null) {
                queryDTO = new JobPostingQueryDTO();
            }

            Map<String, Object> data = jobPostingService.getApprovedJobPostings(queryDTO);

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取岗位列表失败: " + e.getMessage());
        }
    }
}