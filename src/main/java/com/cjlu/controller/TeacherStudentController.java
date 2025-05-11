package com.cjlu.controller;

import com.cjlu.entity.Result;
import com.cjlu.dto.StudentInfoDTO;
import com.cjlu.vo.StudentListVO;
import com.cjlu.service.TeacherStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 教师查看学生信息Controller
 *
 * 使用 @RestController 标记后，getStudentsByTeacher() 和 getStudentDetail() 方法返回的 Result 对象会自动转换为 JSON
 * 前端可以直接接收这些 JSON 数据并处理
 */
@RestController
@RequestMapping("/api/teacher/student")
public class TeacherStudentController {

    @Autowired
    private TeacherStudentService teacherStudentService;

    /**
     * 查询教师岗位下的学生信息（分页）
     */
    @GetMapping("/list")
    public Result getStudentsByTeacher(
            @RequestParam Integer teacherId,
            @RequestParam(required = false) Integer jobId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        try {
            StudentListVO result = teacherStudentService.getStudentsByTeacher(teacherId, jobId, page, size);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("查询学生信息失败: " + e.getMessage());
        }
    }

    /**
     * 查询学生详细信息
     */
    @GetMapping("/detail/{studentId}")
    public Result getStudentDetail(
            @PathVariable Integer studentId, // 从URL路径中获取学生ID，例如 /detail/123 中的123
            @RequestParam Integer teacherId) { // 从URL查询参数中获取教师ID，例如 ?teacherId=456
        try {
            StudentInfoDTO student = teacherStudentService.getStudentDetail(studentId, teacherId);
            return Result.success(student);
        } catch (Exception e) {
            return Result.error("查询学生详情失败: " + e.getMessage());
        }
    }
}