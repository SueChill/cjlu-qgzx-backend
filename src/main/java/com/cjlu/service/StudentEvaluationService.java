package com.cjlu.service;

import com.cjlu.dto.StudentEvaluationDTO;

/**
 * 学生考核评价Service
 */
public interface StudentEvaluationService {

    /**
     * 教师对学生进行月度考核评价
     * @param evaluationDTO 评价信息
     * @param teacherId 教师ID
     * @return 是否成功
     */
    boolean evaluateStudent(StudentEvaluationDTO evaluationDTO, Integer teacherId);
}