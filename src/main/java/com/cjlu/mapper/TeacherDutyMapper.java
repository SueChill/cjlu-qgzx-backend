package com.cjlu.mapper;

import com.cjlu.dto.DutyRecordDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 教师查询学生值班记录Mapper
 */
@Mapper
public interface TeacherDutyMapper {

    /**
     * 查询学生值班记录
     * @param studentId 学生ID
     * @return 值班记录列表
     */
    @Select("SELECT " +
            "DATE_FORMAT(a.sign_in_time, '%Y-%m-%d') AS date, " +
            "CONCAT(DATE_FORMAT(a.sign_in_time, '%H:%i'), '-', DATE_FORMAT(a.sign_out_time, '%H:%i')) AS timeSlot " +
            "FROM Attendance a " +
            "WHERE a.student_id = #{studentId} " +
            "ORDER BY a.sign_in_time ASC")
    @Results({
            @Result(property = "date", column = "date"),
            @Result(property = "timeSlot", column = "timeSlot")
    })
    List<DutyRecordDTO> selectStudentDutyRecords(@Param("studentId") Integer studentId);

    /**
     * 检查学生是否属于教师岗位
     */
    @Select("SELECT COUNT(1) > 0 " +
            "FROM JobPosting jp " +
            "JOIN JobApplication ja ON jp.job_id = ja.job_id " +
            "WHERE jp.publisher_id = #{teacherId} " +
            "AND ja.student_id = #{studentId} " +
            "AND ja.status = 1")
    Boolean checkStudentTeacherRelation(
            @Param("studentId") Integer studentId,
            @Param("teacherId") Integer teacherId);
}