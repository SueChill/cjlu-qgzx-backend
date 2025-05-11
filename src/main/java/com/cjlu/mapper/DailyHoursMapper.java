package com.cjlu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 每日工时Mapper接口
 */
@Mapper
public interface DailyHoursMapper {

    /**
     * 检查是否存在重复记录
     */
    @Select("SELECT COUNT(1) FROM DailyHours WHERE student_id = #{studentId} " +
            "AND job_id = #{jobId} AND work_date = #{workDate}")
    int checkDuplicateRecord(
            @Param("studentId") Integer studentId,
            @Param("jobId") Integer jobId,
            @Param("workDate") String workDate);

    /**
     * 插入每日工时记录
     */
    @Insert("INSERT INTO DailyHours(student_id, job_id, work_date, hours, time_slots, record_code, submit_time, status) " +
            "VALUES(#{studentId}, #{jobId}, #{workDate}, #{hours}, #{timeSlots}, #{recordCode}, NOW(), 'pending')")
    int insertDailyHours(
            @Param("studentId") Integer studentId,
            @Param("jobId") Integer jobId,
            @Param("workDate") String workDate,
            @Param("hours") float hours,
            @Param("timeSlots") String timeSlots,
            @Param("recordCode") String recordCode);

    /**
     * 更新每日工时记录
     */
    @Update("UPDATE DailyHours SET hours = #{hours}, time_slots = #{timeSlots}, " +
            "submit_time = NOW(), status = 'pending' " +
            "WHERE student_id = #{studentId} AND job_id = #{jobId} AND work_date = #{workDate}")
    int updateDailyHours(
            @Param("studentId") Integer studentId,
            @Param("jobId") Integer jobId,
            @Param("workDate") String workDate,
            @Param("hours") float hours,
            @Param("timeSlots") String timeSlots);



    /**
     * 获取学生指定月份的值班记录
     */
    @Select("SELECT work_date, time_slots, hours, status " +
            "FROM DailyHours " +
            "WHERE student_id = #{studentId} " +
            "AND work_date LIKE #{monthPattern} " +
            "AND status = 'approved'")
    List<Map<String, Object>> getMonthlyDutyRecords(
            @Param("studentId") Integer studentId,
            @Param("monthPattern") String monthPattern);
}