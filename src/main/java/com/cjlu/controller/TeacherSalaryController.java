package com.cjlu.controller;

import com.cjlu.dto.MonthlySummaryDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.TeacherSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 教师查询学生工时与工资Controller
 */
@RestController
@RequestMapping("/api/teacher/students")
public class TeacherSalaryController {

    @Autowired
    private TeacherSalaryService teacherSalaryService;

    /**
     * 查询学生本月工时与工资
     */
    @GetMapping("/{studentId}/monthly-summary")
    public Result getStudentMonthlySummary(
            @PathVariable Integer studentId,
            @RequestParam Integer teacherId,
            @RequestParam(required = false) String month) {
        try {
            MonthlySummaryDTO summary = teacherSalaryService.getStudentMonthlySummary(
                    studentId, teacherId, month);
            return Result.success(summary);
        } catch (Exception e) {
            return Result.error("查询月度工时与工资失败: " + e.getMessage());
        }
    }
}