package com.cjlu.service;

import com.cjlu.dto.EvaluationQueryDTO;
import java.util.Map;

/**
 * 管理员评价管理服务
 */
public interface AdminEvaluationService {

    /**
     * 获取学生评价列表
     * @param queryDTO 查询参数
     * @return 评价列表和总数
     */
    Map<String, Object> getStudentEvaluations(EvaluationQueryDTO queryDTO);
}