package com.cjlu.service;

import com.cjlu.dto.DutyRecordDTO;
import java.util.List;

/**
 * 教师查询学生值班记录Service
 */
public interface TeacherDutyService {

    /**
     * 查询学生值班记录
     * @param studentId 学生ID
     * @param teacherId 教师ID
     * @return 值班记录列表
     */
    List<DutyRecordDTO> getStudentDutyRecords(Integer studentId, Integer teacherId);
}