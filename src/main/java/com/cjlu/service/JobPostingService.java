package com.cjlu.service;

import com.cjlu.dto.JobPostingDTO;
import com.cjlu.dto.JobPostingQueryDTO;
import com.cjlu.dto.JobPostingReviewDTO;

import java.util.Map;

public interface JobPostingService {
    /**
     * 发布勤工助学岗位
     * @param jobPostingDTO 岗位信息
     * @param publisherId 发布者ID
     * @return 是否发布成功
     */
    boolean publishJob(JobPostingDTO jobPostingDTO, Integer publisherId);

    /**
     * 获取待审核的岗位列表
     */
    Map<String, Object> getPendingJobPostings(JobPostingQueryDTO queryDTO);

    /**
     * 审核岗位
     */
    boolean reviewJobPosting(JobPostingReviewDTO reviewDTO);
    /**
     * 获取已发布的岗位列表
     */
    Map<String, Object> getApprovedJobPostings(JobPostingQueryDTO queryDTO);
}