package com.cjlu.controller;

import com.cjlu.dto.EvaluationQueryDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.AdminEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理员评价管理Controller
 */
@RestController
@RequestMapping("/api/admin")
public class AdminEvaluationController {

    @Autowired
    private AdminEvaluationService adminEvaluationService;

    /**
     * 获取学生评价列表
     */
    @GetMapping("/student-evaluations")
    public Result getStudentEvaluations(@RequestBody(required = false) EvaluationQueryDTO queryDTO) {
        try {
            // 防止queryDTO为null
            if (queryDTO == null) {
                queryDTO = new EvaluationQueryDTO();
            }

            // 标准化月份格式
            if (queryDTO.getMonth() != null && !queryDTO.getMonth().isEmpty()) {
                queryDTO.setMonth(standardizeMonthFormat(queryDTO.getMonth()));
            }

            Map<String, Object> data = adminEvaluationService.getStudentEvaluations(queryDTO);

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取学生评价列表失败: " + e.getMessage());
        }
    }

    /**
     * 将月份格式标准化为YYYY-MM格式
     * 支持 "2025-5" 转换为 "2025-05"
     */
    private String standardizeMonthFormat(String month) {
        if (month == null || month.isEmpty()) {
            return month;
        }

        String[] parts = month.split("-");
        if (parts.length != 2) {
            return month; // 如果不是YYYY-M格式，则原样返回
        }

        String year = parts[0];
        String monthPart = parts[1];

        // 如果月份部分只有一位数，则前面补0
        if (monthPart.length() == 1) {
            monthPart = "0" + monthPart;
        }

        return year + "-" + monthPart;
    }
}