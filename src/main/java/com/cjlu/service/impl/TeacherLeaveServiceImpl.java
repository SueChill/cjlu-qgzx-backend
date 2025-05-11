package com.cjlu.service.impl;

import com.cjlu.dto.LeaveRecordDTO;
import com.cjlu.mapper.TeacherLeaveMapper;
import com.cjlu.service.TeacherLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师查询学生请假记录Service实现
 */
@Service
public class TeacherLeaveServiceImpl implements TeacherLeaveService {

    @Autowired
    private TeacherLeaveMapper teacherLeaveMapper;

    @Override
    public List<LeaveRecordDTO> getStudentLeaveRecords(Integer teacherId, Integer studentId) {
        // 如果指定了学生ID，检查该学生是否属于该教师
        if (studentId != null) {
            Boolean hasRelation = teacherLeaveMapper.checkStudentTeacherRelation(studentId, teacherId);
            if (Boolean.FALSE.equals(hasRelation)) {
                throw new RuntimeException("该学生不属于您管理的岗位");
            }
        }

        // 查询请假记录
        return teacherLeaveMapper.selectStudentLeaveRecords(teacherId, studentId);
    }
}