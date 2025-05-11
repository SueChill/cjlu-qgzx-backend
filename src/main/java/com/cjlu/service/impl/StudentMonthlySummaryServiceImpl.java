package com.cjlu.service.impl;

import com.cjlu.dto.MonthlySummaryQueryDTO;
import com.cjlu.dto.MonthlyDutySummaryDTO;
import com.cjlu.mapper.DailyHoursMapper;
import com.cjlu.mapper.LeaveRequestMapper;
import com.cjlu.service.StudentMonthlySummaryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 学生月度值班情况服务实现类
 */
@Service
public class StudentMonthlySummaryServiceImpl implements StudentMonthlySummaryService {

    @Autowired
    private DailyHoursMapper dailyHoursMapper;

    @Autowired
    private LeaveRequestMapper leaveRequestMapper;

    @Autowired
    private ObjectMapper objectMapper;

    // 每小时工资标准
    private static final int HOURLY_RATE = 24;

    @Override
    public MonthlyDutySummaryDTO getMonthlySummary(MonthlySummaryQueryDTO queryDTO) {
        try {
            MonthlyDutySummaryDTO summary = new MonthlyDutySummaryDTO();
            List<MonthlyDutySummaryDTO.DutyRecord> allRecords = new ArrayList<>();

            // 获取值班记录
            List<Map<String, Object>> dutyRecords = dailyHoursMapper.getMonthlyDutyRecords(
                    queryDTO.getStudentId(), queryDTO.getMonth() + "%");

            float totalHours = 0;

            // 处理值班记录
            for (Map<String, Object> record : dutyRecords) {
                MonthlyDutySummaryDTO.DutyRecord dutyRecord = new MonthlyDutySummaryDTO.DutyRecord();

                // 设置日期
                dutyRecord.setDate(record.get("work_date").toString());

                // 解析时间段JSON
                String timeSlotsJson = record.get("time_slots").toString();
                List<String> timeSlots = objectMapper.readValue(timeSlotsJson, new TypeReference<List<String>>() {});
                dutyRecord.setTime(timeSlots);

                // 设置状态为出勤
                dutyRecord.setStatus("出勤");

                // 累加工时
                if (record.get("hours") != null) {
                    float hours = Float.parseFloat(record.get("hours").toString());
                    totalHours += hours;
                }

                allRecords.add(dutyRecord);
            }

            // 获取请假记录
            List<Map<String, Object>> leaveRecords = leaveRequestMapper.getMonthlyLeaveRecords(
                    queryDTO.getStudentId(), queryDTO.getMonth() + "%");

            // 处理请假记录
            for (Map<String, Object> record : leaveRecords) {
                MonthlyDutySummaryDTO.DutyRecord leaveRecord = new MonthlyDutySummaryDTO.DutyRecord();

                // 设置日期
                leaveRecord.setDate(record.get("leave_date").toString());

                // 解析时间段JSON
                String timeSlotsJson = record.get("time_slots").toString();
                List<String> timeSlots = objectMapper.readValue(timeSlotsJson, new TypeReference<List<String>>() {});
                leaveRecord.setTime(timeSlots);

                // 设置状态为请假
                leaveRecord.setStatus("请假");

                // 设置请假原因
                if (record.get("reason") != null) {
                    leaveRecord.setReason(record.get("reason").toString());
                }

                allRecords.add(leaveRecord);
            }

            // 按日期排序
            allRecords.sort(Comparator.comparing(MonthlyDutySummaryDTO.DutyRecord::getDate));

            // 设置汇总数据
            summary.setDutyRecords(allRecords);
            summary.setTotalHours(totalHours);
            summary.setSalary((int)(totalHours * HOURLY_RATE));

            return summary;
        } catch (Exception e) {
            throw new RuntimeException("获取月度值班情况失败: " + e.getMessage(), e);
        }
    }
}