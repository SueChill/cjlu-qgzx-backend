package com.cjlu.controller;

import com.cjlu.dto.WorkHoursQueryDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.AdminWorkHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理员工时管理Controller
 */
@RestController
@RequestMapping("/api/admin")
public class AdminWorkHoursController {

    @Autowired
    private AdminWorkHoursService adminWorkHoursService;

    /**
     * 获取学生工时记录
     */
    @GetMapping("/student-work-hours")
    public Result getStudentWorkHours(@RequestBody(required = false) WorkHoursQueryDTO queryDTO) {
        try {
            // 防止queryDTO为null
            if (queryDTO == null) {
                queryDTO = new WorkHoursQueryDTO();
            }

            Map<String, Object> data = adminWorkHoursService.getStudentWorkHours(queryDTO);

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取学生工时记录失败: " + e.getMessage());
        }
    }
}