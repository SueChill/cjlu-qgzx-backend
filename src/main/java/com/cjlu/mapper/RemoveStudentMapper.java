package com.cjlu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 移除学生Mapper
 */
@Mapper
public interface RemoveStudentMapper {

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

    /**
     * 获取学生所在的教师岗位ID列表
     */
    @Select("SELECT ja.job_id " +
            "FROM JobApplication ja " +
            "JOIN JobPosting jp ON ja.job_id = jp.job_id " +
            "WHERE jp.publisher_id = #{teacherId} " +
            "AND ja.student_id = #{studentId} " +
            "AND ja.status = 1")
    List<Integer> getStudentJobIds(
            @Param("studentId") Integer studentId,
            @Param("teacherId") Integer teacherId);

    /**
     * 移除学生与岗位的关联（更新JobApplication状态）
     * 只更新status字段为3，不更新update_time
     */
    @Update("UPDATE JobApplication " +
            "SET status = 3 " +  // 3表示终止/解除关系，移除update_time相关操作
            "WHERE student_id = #{studentId} " +
            "AND job_id = #{jobId} " +
            "AND status = 1")
    int removeStudentFromJob(
            @Param("studentId") Integer studentId,
            @Param("jobId") Integer jobId);

    /**
     * 查询学生基本信息
     */
    @Select("SELECT s.student_id, u.name, u.number " +
            "FROM Student s " +
            "JOIN User u ON s.student_id = u.id " +
            "WHERE s.student_id = #{studentId}")
    Map<String, Object> getStudentInfo(@Param("studentId") Integer studentId);
}