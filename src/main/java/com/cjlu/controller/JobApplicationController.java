package com.cjlu.controller;

import com.cjlu.dto.AppliedPositionDTO;
import com.cjlu.dto.JobApplicationDTO;
import com.cjlu.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

@PostMapping("/apply-position")
public ResponseEntity<?> applyPosition(@RequestBody Map<String, Object> requestData) {
    try {
        JobApplicationDTO dto = new JobApplicationDTO();

        // 从id字段获取学生ID
        if (requestData.containsKey("id")) {
            dto.setStudentId((Integer) requestData.get("id"));
        }

        // 设置岗位ID
        if (requestData.containsKey("jobId")) {
            dto.setJobId((Integer) requestData.get("jobId"));
        }

        // 将time数组转换为字符串
        if (requestData.containsKey("time")) {
            @SuppressWarnings("unchecked")
            List<String> timeList = (List<String>) requestData.get("time");
            dto.setAvailTimes(String.join(",", timeList));
        }

        // 设置申请理由
        if (requestData.containsKey("reason")) {
            dto.setReason((String) requestData.get("reason"));
        }

        boolean result = jobApplicationService.createApplication(dto);
        if (result) {
            return ResponseEntity.ok(Collections.singletonMap("message", "申请提交成功"));
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", "申请提交失败"));
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body(Collections.singletonMap("error", e.getMessage()));
    }
}

    @GetMapping("/applied-positions")
    public ResponseEntity<?> getAppliedPositions(
            @RequestParam("studentId") Integer studentId) {

        if (studentId == null || studentId <= 0) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "无效的学生ID"));
        }

        try {
            List<AppliedPositionDTO> applications =
                    jobApplicationService.getAppliedPositions(studentId);
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Collections.singletonMap("error", "查询申请记录失败"));
        }
    }
}