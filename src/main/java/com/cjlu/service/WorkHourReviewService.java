package com.cjlu.service;

import com.cjlu.dto.WorkHourQueryDTO;
import com.cjlu.dto.WorkHourReviewDTO;
import java.util.Map;

/**
 * 工时审核Service
 */
public interface WorkHourReviewService {

    /**
     * 获取待审核工时记录
     * @param queryDTO 查询参数
     * @param teacherId 教师ID
     * @return 工时记录列表和总数
     */
    Map<String, Object> getPendingWorkHours(WorkHourQueryDTO queryDTO, Integer teacherId);

    /**
     * 审核工时记录
     * @param reviewDTO 审核参数
     * @param teacherId 教师ID
     * @return 是否成功
     */
    boolean reviewWorkHours(WorkHourReviewDTO reviewDTO, Integer teacherId);
}