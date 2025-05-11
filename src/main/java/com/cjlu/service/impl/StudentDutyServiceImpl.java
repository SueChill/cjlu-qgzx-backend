package com.cjlu.service.impl;

import com.cjlu.dto.MonthlyDutyQueryDTO;
import com.cjlu.dto.MonthlyDutySummaryDTO;
import com.cjlu.mapper.DailyHoursMapper;
import com.cjlu.mapper.JobApplicationMapper;
import com.cjlu.mapper.StudentDutyMapper;
import com.cjlu.service.StudentDutyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import com.cjlu.dto.MonthlyDutySummaryDTO.DutyRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Map;

import com.cjlu.dto.DutyTimeDTO;
import org.springframework.transaction.annotation.Transactional;


/**
 * 学生值班服务实现类
 */
@Service
public class StudentDutyServiceImpl implements StudentDutyService {
    @Autowired
    private DailyHoursMapper dailyHoursMapper;

    @Autowired
    private JobApplicationMapper jobApplicationMapper;

    @Autowired
    private StudentDutyMapper studentDutyMapper;

    @Autowired
    private ObjectMapper objectMapper;

    // 工资计算单价（元/小时）
    private static final int HOURLY_RATE = 24;

    @Override
    public MonthlyDutySummaryDTO getMonthlyDutySummary(MonthlyDutyQueryDTO queryDTO, Integer studentId) {
        // 设置默认查询月份为当前月
        String month = queryDTO.getMonth();
        if (month == null || month.trim().isEmpty()) {
            month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        // 创建返回结果
        MonthlyDutySummaryDTO summaryDTO = new MonthlyDutySummaryDTO();
        List<DutyRecord> dutyRecords = new ArrayList<>();

        // 查询出勤记录并整理
        Map<String, List<String>> attendanceMap = aggregateAttendanceByDate(
                studentDutyMapper.getAttendanceRecords(studentId, month));

        // 查询工时记录并整理
        Map<String, DutyRecord> dailyHoursMap = aggregateDailyHoursByDate(
                studentDutyMapper.getDailyHoursRecords(studentId, month));

        // 查询请假记录并整理 (修改版)
        Map<String, List<String>> leaveMap = aggregateLeaveByDate(
                studentDutyMapper.getLeaveRecords(studentId, month));

        // 合并出勤和工时记录
        for (Map.Entry<String, List<String>> entry : attendanceMap.entrySet()) {
            String date = entry.getKey();
            List<String> timeSlots = entry.getValue();

            MonthlyDutySummaryDTO.DutyRecord record = new MonthlyDutySummaryDTO.DutyRecord();
            record.setDate(date);
            record.setTime(timeSlots);
            record.setStatus("出勤");
            dutyRecords.add(record);
        }

        // 添加工时记录
        for (DutyRecord record : dailyHoursMap.values()) {
            if (!attendanceMap.containsKey(record.getDate())) {
                dutyRecords.add(record);
            }
        }

        // 添加请假记录
        for (Map.Entry<String, List<String>> entry : leaveMap.entrySet()) {
            String date = entry.getKey();
            List<String> reasons = entry.getValue();

            if (!attendanceMap.containsKey(date) && !dailyHoursMap.containsKey(date)) {
                MonthlyDutySummaryDTO.DutyRecord record = new MonthlyDutySummaryDTO.DutyRecord();
                record.setDate(date);
                record.setTime(reasons); // 可能需要修改，看您对time字段的用途定义
                record.setReason(reasons.get(0));
                record.setStatus("请假");
                dutyRecords.add(record);
            }
        }

        // 查询累计工时
        float totalHours = studentDutyMapper.getTotalApprovedHours(studentId, month);

        // 计算预估工资
        int salary = Math.round(totalHours * HOURLY_RATE);

        // 设置返回结果
        summaryDTO.setDutyRecords(dutyRecords);
        summaryDTO.setTotalHours(totalHours);
        summaryDTO.setSalary(salary);

        return summaryDTO;
    }

    /**
     * 按日期聚合出勤记录
     */
    private Map<String, List<String>> aggregateAttendanceByDate(List<Map<String, String>> records) {
        Map<String, List<String>> result = new HashMap<>();

        for (Map<String, String> record : records) {
            String date = record.get("date");
            String timeSlot = record.get("time_slot");

            if (!result.containsKey(date)) {
                result.put(date, new ArrayList<>());
            }

            result.get(date).add(timeSlot);
        }

        return result;
    }

    /**
     * 按日期聚合工时记录
     */
    private Map<String, DutyRecord> aggregateDailyHoursByDate(List<Map<String, Object>> records) {
        Map<String, DutyRecord> result = new HashMap<>();

        for (Map<String, Object> record : records) {
            String date = (String) record.get("date");
            String timeSlotJson = (String) record.get("time_slots");
            String status = (String) record.get("status");

            List<String> timeSlots = new ArrayList<>();
            try {
                if (timeSlotJson != null) {
                    timeSlots = objectMapper.readValue(timeSlotJson, new TypeReference<List<String>>() {});
                }
            } catch (Exception e) {
                System.err.println("解析时间段JSON失败: " + e.getMessage());
            }

            // 只显示已批准的记录为"出勤"，其他状态保持原样
            String displayStatus = "approved".equals(status) ? "出勤" : status;

            // 使用无参构造函数创建对象
            DutyRecord dutyRecord = new DutyRecord();
            dutyRecord.setDate(date);
            dutyRecord.setTime(timeSlots);
            dutyRecord.setStatus(displayStatus);

            result.put(date, dutyRecord);
        }

        return result;
    }

    /**
     * 按日期聚合请假记录 (修改版)
     */
    private Map<String, List<String>> aggregateLeaveByDate(List<Map<String, String>> records) {
        Map<String, List<String>> result = new HashMap<>();

        for (Map<String, String> record : records) {
            String date = record.get("date");
            String reason = record.get("reason");
            String time = record.get("time");

            if (!result.containsKey(date)) {
                result.put(date, new ArrayList<>());
            }

            // 组合请假信息
            String leaveInfo = time != null && !time.isEmpty()
                    ? time + " - 请假原因: " + reason
                    : "请假原因: " + reason;

            result.get(date).add(leaveInfo);
        }

        return result;
    }

    @Override
    @Transactional
    public boolean submitDutyTime(DutyTimeDTO dutyTimeDTO) {
        try {
            Integer jobId = dutyTimeDTO.getJobId();

            // 如果没有提供jobId，则自动查询学生已录用的岗位
            if (jobId == null) {
                List<Integer> approvedJobIds = jobApplicationMapper.getApprovedJobIdsByStudentId(dutyTimeDTO.getStudentId());

                if (approvedJobIds.isEmpty()) {
                    throw new RuntimeException("该学生没有被录用的岗位");
                }

                // 如果只有一个岗位，直接使用
                if (approvedJobIds.size() == 1) {
                    jobId = approvedJobIds.get(0);
                } else {
                    // 如果有多个岗位，使用最近录用的岗位（这里简化处理为使用第一个）
                    // 更完善的做法是返回岗位列表让用户选择，或者基于某种规则选择
                    jobId = approvedJobIds.get(0);

                    // 记录日志，提示用户有多个岗位
                    System.out.println("学生ID " + dutyTimeDTO.getStudentId() +
                            " 有多个已录用岗位，自动选择岗位ID: " + jobId);
                }

                // 设置回DTO
                dutyTimeDTO.setJobId(jobId);
            }

            // 验证日期格式
            LocalDate workDate = LocalDate.parse(dutyTimeDTO.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);

            // 计算工时
            float hours = calculateWorkHours(dutyTimeDTO.getTime());

            // 将时间段列表转换为JSON字符串
            String timeSlots = objectMapper.writeValueAsString(dutyTimeDTO.getTime());

            // 检查是否已经提交过该日期的值班记录
            if (dailyHoursMapper.checkDuplicateRecord(dutyTimeDTO.getStudentId(), jobId, dutyTimeDTO.getDate()) > 0) {
                // 更新现有记录
                return dailyHoursMapper.updateDailyHours(
                        dutyTimeDTO.getStudentId(),
                        jobId,
                        dutyTimeDTO.getDate(),
                        hours,
                        timeSlots
                ) > 0;
            } else {
                // 插入新记录
                String recordCode = generateRecordCode(workDate);

                return dailyHoursMapper.insertDailyHours(
                        dutyTimeDTO.getStudentId(),
                        jobId,
                        dutyTimeDTO.getDate(),
                        hours,
                        timeSlots,
                        recordCode
                ) > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException("提交值班时间失败: " + e.getMessage(), e);
        }
    }

    /**
     * 计算总工时
     */
    private float calculateWorkHours(List<String> timeSlots) {
        // 计算工时的代码保持不变...
        float totalHours = 0;

        for (String slot : timeSlots) {
            String[] parts = slot.split("-");
            if (parts.length == 2) {
                String startTime = parts[0].trim();
                String endTime = parts[1].trim();

                // 解析时间并计算小时差
                String[] startParts = startTime.split(":");
                String[] endParts = endTime.split(":");

                if (startParts.length == 2 && endParts.length == 2) {
                    int startHour = Integer.parseInt(startParts[0]);
                    int startMinute = Integer.parseInt(startParts[1]);
                    int endHour = Integer.parseInt(endParts[0]);
                    int endMinute = Integer.parseInt(endParts[1]);

                    double startDecimal = startHour + (startMinute / 60.0);
                    double endDecimal = endHour + (endMinute / 60.0);

                    totalHours += (endDecimal - startDecimal);
                }
            }
        }

        return (float) totalHours;
    }

    /**
     * 生成工时记录编号
     */
    private String generateRecordCode(LocalDate workDate) {
        // 生成记录编号的代码保持不变...
        String dateStr = workDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long timestamp = System.currentTimeMillis() % 1000;
        return "WH" + dateStr + String.format("%03d", timestamp);
    }
}