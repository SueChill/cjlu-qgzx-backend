package com.cjlu.service.impl;

import com.cjlu.dto.DutyRecordDTO;
import com.cjlu.mapper.TeacherDutyMapper;
import com.cjlu.service.TeacherDutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师查询学生值班记录Service实现
 */
@Service
public class TeacherDutyServiceImpl implements TeacherDutyService {

    @Autowired
    private TeacherDutyMapper teacherDutyMapper;

    @Override
    public List<DutyRecordDTO> getStudentDutyRecords(Integer studentId, Integer teacherId) {
        // 检查权限
        Boolean hasRelation = teacherDutyMapper.checkStudentTeacherRelation(studentId, teacherId);
        if (Boolean.FALSE.equals(hasRelation)) {
            throw new RuntimeException("该学生不属于您管理的岗位");
        }

        // 查询原始值班记录
        List<DutyRecordDTO> rawRecords = teacherDutyMapper.selectStudentDutyRecords(studentId);

        // 处理数据：将同一天的多个时间段合并到一条记录中
        Map<String, List<String>> dateTimeMap = new HashMap<>();

        // 遍历原始记录，按日期分组
        for (DutyRecordDTO record : rawRecords) {
            String date = record.getDate();
            String timeSlot = record.getTimeSlot(); // 使用临时字段

            if (date != null && timeSlot != null) {
                List<String> timeSlots = dateTimeMap.getOrDefault(date, new ArrayList<>());
                timeSlots.add(timeSlot);
                dateTimeMap.put(date, timeSlots);
            }
        }

        // 重新构建结果
        List<DutyRecordDTO> result = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : dateTimeMap.entrySet()) {
            DutyRecordDTO dto = new DutyRecordDTO();
            dto.setDate(entry.getKey());
            dto.setTime(entry.getValue());
            result.add(dto);
        }

        return result;
    }






}
