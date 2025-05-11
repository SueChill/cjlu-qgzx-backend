package com.cjlu.controller;

import com.cjlu.entity.Result;
import com.cjlu.service.RemoveStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 移除学生Controller
 */
@RestController
@RequestMapping("/api/teacher")
public class RemoveStudentController {

    @Autowired
    private RemoveStudentService removeStudentService;

    /**
     * 删除学生（移除学生与岗位的关联）
     */
    @PostMapping("/students/remove")
    public Result removeStudent(
            @RequestParam Integer studentId,
            @RequestParam Integer teacherId) {
        try {
            boolean success = removeStudentService.removeStudent(studentId, teacherId);
            if (success) {
                return Result.success(null);
            } else {
                return Result.error("移除失败，请确认学生与岗位的关联状态");
            }
        } catch (Exception e) {
            return Result.error("移除学生失败: " + e.getMessage());
        }
    }
}