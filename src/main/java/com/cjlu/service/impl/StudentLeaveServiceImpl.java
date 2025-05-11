package com.cjlu.service.impl;

import com.cjlu.dto.StudentLeaveDTO;
import com.cjlu.mapper.LeaveRequestMapper;
import com.cjlu.service.StudentLeaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 学生请假服务实现类
 */
@Service
public class StudentLeaveServiceImpl implements StudentLeaveService {

    @Autowired
    private LeaveRequestMapper leaveRequestMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public boolean submitLeaveRequest(StudentLeaveDTO leaveDTO, Integer jobId) {
        try {
            // 验证日期格式
            LocalDate leaveDate = LocalDate.parse(leaveDTO.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);

            // 将时间段列表转换为JSON字符串
            String timeSlots = objectMapper.writeValueAsString(leaveDTO.getTime());

            // 检查是否已经提交过该日期的请假记录
            if (leaveRequestMapper.checkDuplicateRequest(leaveDTO.getStudentId(), jobId, leaveDTO.getDate()) > 0) {
                // 更新现有记录
                return leaveRequestMapper.updateLeaveRequest(
                        leaveDTO.getStudentId(),
                        jobId,
                        leaveDTO.getDate(),
                        timeSlots,
                        leaveDTO.getReason()
                ) > 0;
            } else {
                // 生成请假申请编号
                String requestCode = generateRequestCode(leaveDate);

                // 插入新记录
                return leaveRequestMapper.insertLeaveRequest(
                        leaveDTO.getStudentId(),
                        jobId,
                        leaveDTO.getDate(),
                        timeSlots,
                        leaveDTO.getReason(),
                        requestCode,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ) > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException("提交请假申请失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成请假申请编号
     */
    private String generateRequestCode(LocalDate leaveDate) {
        String dateStr = leaveDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 生成短UUID，保证唯一性
        String shortUuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return "LR" + dateStr + shortUuid;
    }
}