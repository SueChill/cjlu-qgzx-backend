package com.cjlu.controller;

import com.cjlu.dto.StudentEvaluationDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.StudentEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 学生考核评价Controller
 */
@RestController
@RequestMapping("/api/teacher")
public class StudentEvaluationController {

    @Autowired
    private StudentEvaluationService studentEvaluationService;

    /**
     * 教师对学生进行月度考核评价
     */
    @PostMapping("/evaluations")
    public Result evaluateStudent(
            @RequestBody StudentEvaluationDTO evaluationDTO,
            @RequestParam Integer teacherId) {
        try {
            boolean success = studentEvaluationService.evaluateStudent(evaluationDTO, teacherId);

            if (success) {
                return Result.success("评价提交成功");
            } else {
                return Result.error("评价提交失败");
            }
        } catch (IllegalArgumentException e) {
            return Result.error("评价提交失败: " + e.getMessage());
        } catch (RuntimeException e) {
            return Result.error("评价提交失败: " + e.getMessage());
        } catch (Exception e) {
            return Result.error("评价提交失败: 系统错误");
        }
    }
}