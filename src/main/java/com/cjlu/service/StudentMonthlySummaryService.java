package com.cjlu.service;

import com.cjlu.dto.MonthlySummaryQueryDTO;
import com.cjlu.dto.MonthlyDutySummaryDTO;

/**
 * 学生月度值班情况服务接口
 */
public interface StudentMonthlySummaryService {

    /**
     * 获取月度值班情况汇总
     * @param queryDTO 查询参数
     * @return 月度值班情况汇总
     */
    MonthlyDutySummaryDTO getMonthlySummary(MonthlySummaryQueryDTO queryDTO);
}