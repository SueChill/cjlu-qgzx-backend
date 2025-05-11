package com.cjlu.controller;

import com.cjlu.dto.DutyTimeDTO;
import com.cjlu.dto.MonthlyDutyQueryDTO;
import com.cjlu.dto.MonthlyDutySummaryDTO;
import com.cjlu.entity.Result;
import com.cjlu.mapper.JobApplicationMapper;
import com.cjlu.service.StudentDutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生值班相关控制器
 */
@RestController
@RequestMapping("/api/student")
public class StudentDutyController {

    @Autowired
    private StudentDutyService studentDutyService;

    @Autowired
    private JobApplicationMapper jobApplicationMapper;

    /**
     * 学生提交值班时间
     */
    @PostMapping("/submit-duty-time")
    public Result submitDutyTime(@RequestBody DutyTimeDTO dutyTimeDTO, HttpServletRequest request) {
        try {
            // 从JWT中获取学生ID，如果请求中已提供则使用请求中的ID
            Integer studentId = (Integer) request.getAttribute("userId");
            if (dutyTimeDTO.getStudentId() != null) {
                studentId = dutyTimeDTO.getStudentId();
            }

            // 如果没有获取到学生ID，返回错误
            if (studentId == null) {
                return Result.error("请提供有效的学生ID");
            }

            // 设置studentId到DTO中
            dutyTimeDTO.setStudentId(studentId);

            // 如果没有提供jobId，查询岗位信息并返回
            if (dutyTimeDTO.getJobId() == null) {
                List<Map<String, Object>> jobs = jobApplicationMapper.getApprovedJobDetailsForStudent(studentId);

                if (jobs.isEmpty()) {
                    return Result.error("您没有被录用的岗位，无法提交值班时间");
                }

                // 记录所使用的岗位信息
                Map<String, Object> jobInfo = jobs.get(0);
                dutyTimeDTO.setJobId((Integer) jobInfo.get("jobId"));
            }

            // 调用服务层处理业务逻辑
            boolean success = studentDutyService.submitDutyTime(dutyTimeDTO);

            if (success) {
                // 构建响应，包含自动匹配的岗位信息
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("jobId", dutyTimeDTO.getJobId());

                return Result.success("提交成功");
            } else {
                return Result.error("提交值班时间失败");
            }
        } catch (Exception e) {
            return Result.error("提交值班时间出错: " + e.getMessage());
        }
    }

    /**
     * 获取学生已录用的岗位列表
     */
    @GetMapping("/approved-jobs")
    public Result getApprovedJobs(@RequestParam Integer studentId) {
        try {
            List<Map<String, Object>> jobs = jobApplicationMapper.getApprovedJobDetailsForStudent(studentId);

            if (jobs.isEmpty()) {
                return Result.error("您没有被录用的岗位");
            }

            return Result.success(jobs);
        } catch (Exception e) {
            return Result.error("获取岗位信息失败: " + e.getMessage());
        }
    }


//    /**
//     * 获取学生本月值班情况
//     */
//    @GetMapping("/monthly-duty-summary")
//    public Result getMonthlyDutySummary(
//            @RequestBody(required = false) MonthlyDutyQueryDTO queryDTO,
//            @RequestParam Integer studentId) {
//        try {
//            // 防止queryDTO为null
//            if (queryDTO == null) {
//                queryDTO = new MonthlyDutyQueryDTO();
//            }
//
//            MonthlyDutySummaryDTO summary = studentDutyService.getMonthlyDutySummary(queryDTO, studentId);
//
//            return Result.success(summary);
//        } catch (Exception e) {
//            return Result.error("获取月度值班汇总失败: " + e.getMessage());
//        }
//    }
}