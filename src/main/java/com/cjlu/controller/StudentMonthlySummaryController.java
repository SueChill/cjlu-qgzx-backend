package com.cjlu.controller;

import com.cjlu.dto.MonthlySummaryQueryDTO;
import com.cjlu.dto.MonthlyDutySummaryDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.StudentMonthlySummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 学生月度值班情况控制器
 */
@RestController
@RequestMapping("/api/student")
public class StudentMonthlySummaryController {

    @Autowired
    private StudentMonthlySummaryService summaryService;

    /**
     * 获取本月值班情况
     */
    @GetMapping("/monthly-duty-summary")
    public Result getMonthlySummary(
            @RequestParam(required = false) Integer studentId,
            @RequestParam(required = false) String month,
            HttpServletRequest request) {
        try {
            // 从JWT中获取学生ID，如果请求中已提供则使用请求中的ID
            Integer userId = (Integer) request.getAttribute("userId");
            if (studentId != null) {
                userId = studentId;
            }

            // 如果没有获取到学生ID，返回错误
            if (userId == null) {
                return Result.error("请提供有效的学生ID");
            }

            // 如果未指定月份，使用当前月份
            if (month == null || month.isEmpty()) {
                month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            }

            // 构建查询参数
            MonthlySummaryQueryDTO queryDTO = new MonthlySummaryQueryDTO();
            queryDTO.setStudentId(userId);
            queryDTO.setMonth(month);

            // 获取月度值班情况汇总
            MonthlyDutySummaryDTO summary = summaryService.getMonthlySummary(queryDTO);

            return Result.success(summary);
        } catch (Exception e) {
            return Result.error("获取月度值班情况失败: " + e.getMessage());
        }
    }
}