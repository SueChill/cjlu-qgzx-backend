package com.cjlu.mapper;

import com.cjlu.entity.StudentEvaluation;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentEvaluationMapper {

    /**
     * 查询教师管理的学生信息
     */
    @Select("SELECT ja.student_id, ja.job_id " +
            "FROM JobApplication ja " +
            "JOIN JobPosting jp ON ja.job_id = jp.job_id " +
            "WHERE jp.publisher_id = #{teacherId} " +
            "AND ja.student_id = #{studentId} " +
            "AND ja.status = 1")  // 状态为1表示已通过/已录用
    List<Map<String, Object>> getTeacherStudentRelation(
            @Param("teacherId") Integer teacherId,
            @Param("studentId") Integer studentId);

    /**
     * 查询是否已有该月评价
     */
    @Select("SELECT COUNT(1) " +
            "FROM StudentEvaluation " +
            "WHERE student_id = #{studentId} " +
            "AND job_id = #{jobId} " +
            "AND month = #{month}")
    int checkExistingEvaluation(
            @Param("studentId") Integer studentId,
            @Param("jobId") Integer jobId,
            @Param("month") String month);

    /**
     * 插入学生评价
     */
    @Insert("INSERT INTO StudentEvaluation(student_id, teacher_id, job_id, month, comment, evaluation_time) " +
            "VALUES(#{studentId}, #{teacherId}, #{jobId}, #{month}, #{comment}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "evaluationId")
    int insertEvaluation(StudentEvaluation evaluation);

    /**
     * 更新学生评价
     */
    @Update("UPDATE StudentEvaluation " +
            "SET comment = #{comment}, evaluation_time = NOW() " +
            "WHERE student_id = #{studentId} " +
            "AND job_id = #{jobId} " +
            "AND month = #{month}")
    int updateEvaluation(StudentEvaluation evaluation);
}