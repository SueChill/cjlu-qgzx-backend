package com.cjlu.controller;

import com.cjlu.dto.LeaveRecordDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.TeacherLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师查询学生请假记录Controller
 */
@RestController
@RequestMapping("/api/teacher/leave")
public class TeacherLeaveController {

    @Autowired
    private TeacherLeaveService teacherLeaveService;

    /**
     * 查询学生请假记录
     */
    @GetMapping("/list")
    public Result getStudentLeaveRecords(
            @RequestParam Integer teacherId,
            @RequestParam(required = false) Integer studentId) {
        try {
            List<LeaveRecordDTO> records = teacherLeaveService.getStudentLeaveRecords(
                    teacherId, studentId);
            return Result.success(records);
        } catch (Exception e) {
            return Result.error("查询请假记录失败: " + e.getMessage());
        }
    }
}