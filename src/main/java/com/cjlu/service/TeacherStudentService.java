package com.cjlu.service;

import com.cjlu.dto.StudentInfoDTO;
import com.cjlu.vo.StudentListVO;

/**
 * 教师查看学生信息Service
 */
public interface TeacherStudentService {

    /**
     * 查询教师岗位下的学生信息（分页）
     * @param teacherId 教师ID
     * @param jobId 岗位ID（可选）
     * @param page 页码
     * @param size 每页大小
     * @return 学生信息分页结果
     */
    StudentListVO getStudentsByTeacher(Integer teacherId, Integer jobId, Integer page, Integer size);

    /**
     * 查询学生详细信息
     * @param studentId 学生ID
     * @param teacherId 教师ID
     * @return 学生详细信息
     */
    StudentInfoDTO getStudentDetail(Integer studentId, Integer teacherId);
}