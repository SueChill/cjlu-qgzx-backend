package com.cjlu.service.impl;

import com.cjlu.dto.MonthlySummaryDTO;
import com.cjlu.mapper.TeacherSalaryMapper;
import com.cjlu.service.TeacherSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 教师查询学生工时与工资Service实现
 */
@Service
public class TeacherSalaryServiceImpl implements TeacherSalaryService {

    // 工资计算时的时薪
    private static final int HOURLY_RATE = 24;

    @Autowired
    private TeacherSalaryMapper teacherSalaryMapper;

    @Override
    public MonthlySummaryDTO getStudentMonthlySummary(
            Integer studentId,
            Integer teacherId,
            String month) {

        // 检查权限
        Boolean hasRelation = teacherSalaryMapper.checkStudentTeacherRelation(studentId, teacherId);
        if (Boolean.FALSE.equals(hasRelation)) {
            throw new RuntimeException("该学生不属于您管理的岗位");
        }

        // 如果未指定月份，默认使用当前月份
        if (month == null || month.trim().isEmpty()) {
            LocalDate now = LocalDate.now();
            month = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        // 查询总工时
        Double totalHours = teacherSalaryMapper.selectStudentMonthlyHours(studentId, month);

        // 如果没有工时记录，默认为0
        if (totalHours == null) {
            totalHours = 0.0;
        }

        // 计算工资（工时 × 时薪）
        Integer salary = (int) Math.round(totalHours * HOURLY_RATE);

        // 封装结果
        MonthlySummaryDTO result = new MonthlySummaryDTO();
        result.setMonth(month);
        result.setTotalHours(totalHours);
        result.setSalary(salary);

        return result;
    }
}