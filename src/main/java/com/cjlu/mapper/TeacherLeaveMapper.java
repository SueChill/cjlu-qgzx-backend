package com.cjlu.mapper;

import com.cjlu.dto.LeaveRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教师查询学生请假记录Mapper
 */
@Mapper
public interface TeacherLeaveMapper {

    /**
     * 查询教师关联学生的请假记录
     * @param teacherId 教师ID
     * @param studentId 学生ID(可选)
     * @return 请假记录列表
     */
    List<LeaveRecordDTO> selectStudentLeaveRecords(
            @Param("teacherId") Integer teacherId,
            @Param("studentId") Integer studentId);

    /**
     * 检查学生是否属于教师岗位
     */
    Boolean checkStudentTeacherRelation(
            @Param("studentId") Integer studentId,
            @Param("teacherId") Integer teacherId);
}