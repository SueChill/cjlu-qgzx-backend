package com.cjlu.service;

import com.cjlu.dto.DutyTimeDTO;
import com.cjlu.dto.MonthlyDutyQueryDTO;
import com.cjlu.dto.MonthlyDutySummaryDTO;

/**
 * 学生值班服务接口
 */
public interface StudentDutyService {

    /**
     * 获取学生月度值班汇总信息
     * @param queryDTO 查询参数
     * @param studentId 学生ID
     * @return 月度值班汇总信息
     */
    MonthlyDutySummaryDTO getMonthlyDutySummary(MonthlyDutyQueryDTO queryDTO, Integer studentId);


    /**
     * 提交值班时间
     * @param dutyTimeDTO 值班时间数据
     * @return 是否提交成功
     */
    boolean submitDutyTime(DutyTimeDTO dutyTimeDTO);
}