package com.cjlu.service.impl;

import com.cjlu.dto.AdminEvaluationResultDTO;
import com.cjlu.dto.EvaluationQueryDTO;
import com.cjlu.mapper.AdminEvaluationMapper;
import com.cjlu.service.AdminEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminEvaluationServiceImpl implements AdminEvaluationService {

    @Autowired
    private AdminEvaluationMapper adminEvaluationMapper;

    @Override
    public Map<String, Object> getStudentEvaluations(EvaluationQueryDTO queryDTO) {
        // 计算分页偏移量
        int pageSize = queryDTO.getPageSize();
        int offset = (queryDTO.getPage() - 1) * pageSize;

        // 查询评价列表 - 使用单月份查询
        List<AdminEvaluationResultDTO> evaluations = adminEvaluationMapper.getEvaluations(
                queryDTO.getStudent(),
                queryDTO.getTeacher(),
                queryDTO.getMonth(),
                offset,
                pageSize);

        // 查询总记录数
        Integer total = adminEvaluationMapper.countEvaluations(
                queryDTO.getStudent(),
                queryDTO.getTeacher(),
                queryDTO.getMonth());

        // 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("evaluations", evaluations);

        return result;
    }
}