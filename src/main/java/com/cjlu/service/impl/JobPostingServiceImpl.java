package com.cjlu.service.impl;

import com.cjlu.dto.JobPostingDTO;
import com.cjlu.dto.JobPostingQueryDTO;
import com.cjlu.dto.JobPostingReviewDTO;
import com.cjlu.mapper.JobPostingMapper;
import com.cjlu.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobPostingServiceImpl implements JobPostingService {

    @Autowired
    private JobPostingMapper jobPostingMapper;

    /**
     * 发布勤工助学岗位
     */
    @Override
    public boolean publishJob(JobPostingDTO jobPostingDTO, Integer publisherId) {
        try {
            // 将DTO转换为数据库实体并保存
            int rowsAffected = jobPostingMapper.insertJobPosting(
                    jobPostingDTO.getJobTitle(),
                    jobPostingDTO.getRequirements(),
                    jobPostingDTO.getTime(),
                    jobPostingDTO.getLocation(),
                    jobPostingDTO.getPhone(),
                    publisherId
            );

            // 如果受影响行数大于0，表示插入成功
            return rowsAffected > 0;
        } catch (Exception e) {
            // 记录错误并返回失败
            System.err.println("发布岗位失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 获取待审核的岗位列表
     */
    @Override
    public Map<String, Object> getPendingJobPostings(JobPostingQueryDTO queryDTO) {
        // 计算分页偏移量
        int pageSize = queryDTO.getPageSize();
        int offset = (queryDTO.getPage() - 1) * pageSize;

        // 查询待审核岗位列表
        List<JobPostingDTO> positions = jobPostingMapper.getPendingPositions(
                queryDTO.getKeyword(),
                offset,
                pageSize);

        // 查询总记录数
        Integer total = jobPostingMapper.countPendingPositions(queryDTO.getKeyword());

        // 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("positions", positions);

        return result;
    }

    /**
     * 审核岗位
     */
    @Override
    public boolean reviewJobPosting(JobPostingReviewDTO reviewDTO) {
        // 检查岗位是否存在
        if (jobPostingMapper.checkPositionExists(reviewDTO.getPositionId()) == 0) {
            return false;
        }

        // 审核状态转换
        String dbStatus;
        if ("approve".equals(reviewDTO.getStatus())) {
            dbStatus = "approved";
        } else if ("reject".equals(reviewDTO.getStatus())) {
            dbStatus = "rejected";
        } else {
            // 非法状态值
            return false;
        }

        // 更新审核状态
        int rows = jobPostingMapper.updatePositionStatus(
                reviewDTO.getPositionId(),
                dbStatus);

        return rows > 0;
    }

    /**
     * 获取已审核通过的岗位列表（公示岗位）
     */
    @Override
    public Map<String, Object> getApprovedJobPostings(JobPostingQueryDTO queryDTO) {
        // 计算分页偏移量
        int pageSize = queryDTO.getPageSize();
        int offset = (queryDTO.getPage() - 1) * pageSize;

        // 查询已审核通过的岗位列表
        List<JobPostingDTO> positions = jobPostingMapper.getApprovedPositions(
                queryDTO.getKeyword(),
                offset,
                pageSize);

        // 查询总记录数
        Integer total = jobPostingMapper.countApprovedPositions(queryDTO.getKeyword());

        // 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("positions", positions);

        return result;




    }


}