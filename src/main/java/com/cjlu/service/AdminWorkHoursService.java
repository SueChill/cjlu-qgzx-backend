package com.cjlu.service;

import com.cjlu.dto.WorkHoursQueryDTO;
import java.util.Map;

/**
 * 管理员工时管理服务
 */
public interface AdminWorkHoursService {

    /**
     * 获取学生工时记录
     * @param queryDTO 查询参数
     * @return 工时记录和总数
     */
    Map<String, Object> getStudentWorkHours(WorkHoursQueryDTO queryDTO);
}