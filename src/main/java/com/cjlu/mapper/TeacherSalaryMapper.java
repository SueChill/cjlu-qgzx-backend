package com.cjlu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherSalaryMapper {

    /**
     * 查询学生某月的总工时
     * @param studentId 学生ID
     * @param yearMonth 年月，格式：YYYY-MM
     * @return 总工时
     */
    @Select("SELECT IFNULL(SUM(hours), 0) " +
            "FROM DailyHours " +
            "WHERE student_id = #{studentId} " +
            "AND DATE_FORMAT(work_date, '%Y-%m') = #{yearMonth}")
    Double selectStudentMonthlyHours(
            @Param("studentId") Integer studentId,
            @Param("yearMonth") String yearMonth);

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