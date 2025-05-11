package com.cjlu.controller;

import com.cjlu.dto.DutyRecordDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.TeacherDutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师查询学生值班记录Controller
 */
@RestController
@RequestMapping("/api/teacher/students")
public class TeacherDutyController {

    @Autowired
    private TeacherDutyService teacherDutyService;

    /**
     * 查询学生值班记录
     */
    @GetMapping("/{studentId}/duty-records")
    public Result getStudentDutyRecords(
            @PathVariable Integer studentId,
            @RequestParam Integer teacherId) {
        try {
            List<DutyRecordDTO> records = teacherDutyService.getStudentDutyRecords(studentId, teacherId);
            return Result.success(records);
        } catch (Exception e) {
            return Result.error("查询值班记录失败: " + e.getMessage());
        }
    }
}