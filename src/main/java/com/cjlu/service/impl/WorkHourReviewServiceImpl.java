package com.cjlu.service.impl;

import com.cjlu.dto.WorkHourQueryDTO;
import com.cjlu.dto.WorkHourRecordDTO;
import com.cjlu.dto.WorkHourReviewDTO;
import com.cjlu.mapper.WorkHourReviewMapper;
import com.cjlu.service.WorkHourReviewService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkHourReviewServiceImpl implements WorkHourReviewService {

    @Autowired
    private WorkHourReviewMapper workHourReviewMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Map<String, Object> getPendingWorkHours(WorkHourQueryDTO queryDTO, Integer teacherId) {
        // 设置默认查询月份为当前月
        String month = queryDTO.getMonth();
        if (month == null || month.trim().isEmpty()) {
            month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        // 计算分页偏移量
        int pageSize = queryDTO.getPageSize();
        int offset = (queryDTO.getPage() - 1) * pageSize;

        // 查询工时记录
        List<WorkHourRecordDTO> records = workHourReviewMapper.getPendingWorkHours(teacherId, month, offset, pageSize);

        // 处理时间段JSON字符串
        for (WorkHourRecordDTO record : records) {
            try {
                if (record.getTimeSlots() != null) {
                    // 检查timeSlots的类型
                    if (record.getTimeSlots() instanceof String) {
                        // 如果是字符串，则需要解析JSON
                        String timeSlotsJson = (String) record.getTimeSlots();
                        List<String> timeSlotsList = objectMapper.readValue(
                                timeSlotsJson,
                                new TypeReference<List<String>>() {});
                        record.setTimeSlots(timeSlotsList);
                    }
                    // 如果已经是List<String>，无需处理
                } else {
                    // 如果为null则设置空列表
                    record.setTimeSlots(new ArrayList<String>());
                }
            } catch (Exception e) {
                // 解析失败时设置为空列表
                record.setTimeSlots(new ArrayList<String>());
                System.err.println("JSON解析错误: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // 查询总记录数
        int total = workHourReviewMapper.countPendingWorkHours(teacherId, month);

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("records", records);

        return result;
    }

    @Override
    @Transactional
    public boolean reviewWorkHours(WorkHourReviewDTO reviewDTO, Integer teacherId) {
        // 此方法保持不变
        if (reviewDTO.getReviews() == null || reviewDTO.getReviews().isEmpty()) {
            throw new IllegalArgumentException("审核记录不能为空");
        }

        boolean allSuccess = true;

        for (WorkHourReviewDTO.ReviewItem item : reviewDTO.getReviews()) {
            // 验证参数
            if (item.getRecordId() == null || item.getRecordId().trim().isEmpty()) {
                throw new IllegalArgumentException("工时记录ID不能为空");
            }

            if (item.getStatus() == null ||
                    (!item.getStatus().equals("approved") && !item.getStatus().equals("rejected"))) {
                throw new IllegalArgumentException("审核状态无效，必须为approved或rejected");
            }

            // 检查记录是否归属于该教师
            int count = workHourReviewMapper.checkWorkHourBelongsToTeacher(item.getRecordId(), teacherId);
            if (count <= 0) {
                throw new RuntimeException("工时记录 " + item.getRecordId() + " 不属于您管理的学生");
            }

            // 更新工时记录状态
            int result = workHourReviewMapper.updateWorkHourStatus(
                    item.getRecordId(), item.getStatus(), teacherId);

            if (result <= 0) {
                allSuccess = false;
            }
        }

        return allSuccess;
    }
}