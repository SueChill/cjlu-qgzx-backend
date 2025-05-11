package com.cjlu.service;

import com.cjlu.dto.MonthlySummaryDTO;

/**
 * 教师查询学生工时与工资Service
 */
public interface TeacherSalaryService {

    /**
     * 查询学生每月工时与工资
     * @param studentId 学生ID
     * @param teacherId 教师ID
     * @param month 月份，格式：YYYY-MM，如空则默认为当前月份
     * @return 月度工时与工资摘要
     */
    MonthlySummaryDTO getStudentMonthlySummary(
            Integer studentId,
            Integer teacherId,
            String month);
}