package com.cjlu.controller;

import com.cjlu.dto.StudentLeaveDTO;
import com.cjlu.entity.Result;
import com.cjlu.mapper.JobApplicationMapper;
import com.cjlu.service.StudentLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生请假控制器
 */
@RestController
@RequestMapping("/api/student")
public class StudentLeaveController {

    @Autowired
    private StudentLeaveService studentLeaveService;

    @Autowired
    private JobApplicationMapper jobApplicationMapper;

    /**
     * 提交请假申请
     */
    @PostMapping("/leave-request")
    public Result leaveRequest(@RequestBody StudentLeaveDTO leaveDTO, HttpServletRequest request) {
        try {
            // 从JWT中获取学生ID，如果请求中已提供则使用请求中的ID
            Integer studentId = (Integer) request.getAttribute("userId");
            if (leaveDTO.getStudentId() != null) {
                studentId = leaveDTO.getStudentId();
            }

            // 如果没有获取到学生ID，返回错误
            if (studentId == null) {
                return Result.error("请提供有效的学生ID");
            }

            // 设置studentId到DTO中
            leaveDTO.setStudentId(studentId);

            // 查询学生已录用的岗位
            List<Map<String, Object>> jobs = jobApplicationMapper.getApprovedJobDetailsForStudent(studentId);

            if (jobs.isEmpty()) {
                return Result.error("您没有被录用的岗位，无法提交请假申请");
            }

            // 记录所使用的岗位信息
            Map<String, Object> jobInfo = jobs.get(0);
            Integer jobId = (Integer) jobInfo.get("jobId");

            // 调用服务层处理业务逻辑
            boolean success = studentLeaveService.submitLeaveRequest(leaveDTO, jobId);

            if (success) {
                // 构建响应，包含岗位信息
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("jobId", jobId);
                responseData.put("jobTitle", jobInfo.get("jobTitle"));

                return Result.success("请假申请提交成功");
            } else {
                return Result.error("请假申请提交失败");
            }
        } catch (Exception e) {
            return Result.error("请假申请出错: " + e.getMessage());
        }
    }
}