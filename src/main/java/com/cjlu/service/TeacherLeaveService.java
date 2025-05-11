package com.cjlu.service;

import com.cjlu.dto.LeaveRecordDTO;
import java.util.List;

/**
 * 教师查询学生请假记录Service
 */
public interface TeacherLeaveService {

    /**
     * 查询教师关联学生的请假记录
     * @param teacherId 教师ID
     * @param studentId 学生ID(可选)
     * @return 请假记录列表
     */
    List<LeaveRecordDTO> getStudentLeaveRecords(Integer teacherId, Integer studentId);
}