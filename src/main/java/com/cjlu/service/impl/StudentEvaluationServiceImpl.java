package com.cjlu.service.impl;

import com.cjlu.dto.StudentEvaluationDTO;
import com.cjlu.entity.StudentEvaluation;
import com.cjlu.mapper.StudentEvaluationMapper;
import com.cjlu.service.StudentEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Service
public class StudentEvaluationServiceImpl implements StudentEvaluationService {

    @Autowired
    private StudentEvaluationMapper studentEvaluationMapper;

    @Override
    @Transactional
    public boolean evaluateStudent(StudentEvaluationDTO evaluationDTO, Integer teacherId) {
        // 1. 参数验证
        if (evaluationDTO.getStudentId() == null) {
            throw new IllegalArgumentException("学生ID不能为空");
        }
        if (evaluationDTO.getMonth() == null || evaluationDTO.getMonth().trim().isEmpty()) {
            throw new IllegalArgumentException("评价月份不能为空");
        }
        // 评价内容可以为空，移除相关验证

        // 2. 验证月份格式 (YYYY-MM)
        try {
            YearMonth.parse(evaluationDTO.getMonth(), DateTimeFormatter.ofPattern("yyyy-MM"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("月份格式不正确，应为YYYY-MM格式");
        }

        // 3. 直接使用studentId，因为它已经是Integer类型
        Integer studentId = evaluationDTO.getStudentId();

        // 4. 检查学生是否属于该教师管理的岗位
        List<Map<String, Object>> relations = studentEvaluationMapper.getTeacherStudentRelation(teacherId, studentId);
        if (relations.isEmpty()) {
            throw new RuntimeException("该学生不属于您管理的岗位");
        }

        // 5. 处理评价（可能有多个岗位关联）
        boolean success = false;
        for (Map<String, Object> relation : relations) {
            Integer jobId = (Integer) relation.get("job_id");

            // 创建评价实体
            StudentEvaluation evaluation = new StudentEvaluation();
            evaluation.setStudentId(studentId);
            evaluation.setTeacherId(teacherId);
            evaluation.setJobId(jobId);
            evaluation.setMonth(evaluationDTO.getMonth());
            evaluation.setComment(evaluationDTO.getComment()); // 可以为null或空字符串

            // 检查是否已有该月评价
            int exists = studentEvaluationMapper.checkExistingEvaluation(studentId, jobId, evaluationDTO.getMonth());

            int result;
            if (exists > 0) {
                // 更新现有评价
                result = studentEvaluationMapper.updateEvaluation(evaluation);
            } else {
                // 插入新评价
                result = studentEvaluationMapper.insertEvaluation(evaluation);
            }

            if (result > 0) {
                success = true;
            }
        }

        return success;
    }
}