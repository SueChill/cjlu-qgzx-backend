package com.cjlu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 请假申请Mapper接口
 */
@Mapper
public interface LeaveRequestMapper {

    /**
     * 检查是否存在重复请假记录
     */
    @Select("SELECT COUNT(1) FROM LeaveRequest WHERE student_id = #{studentId} " +
            "AND job_id = #{jobId} AND leave_date = #{leaveDate}")
    int checkDuplicateRequest(
            @Param("studentId") Integer studentId,
            @Param("jobId") Integer jobId,
            @Param("leaveDate") String leaveDate);

    /**
     * 插入请假申请记录
     */
    @Insert("INSERT INTO LeaveRequest(student_id, job_id, leave_date, time_slots, reason, request_code, submit_time, status) " +
            "VALUES(#{studentId}, #{jobId}, #{leaveDate}, #{timeSlots}, #{reason}, #{requestCode}, #{submitTime}, 'pending')")
    int insertLeaveRequest(
            @Param("studentId") Integer studentId,
            @Param("jobId") Integer jobId,
            @Param("leaveDate") String leaveDate,
            @Param("timeSlots") String timeSlots,
            @Param("reason") String reason,
            @Param("requestCode") String requestCode,
            @Param("submitTime") String submitTime);

    /**
     * 更新请假申请记录
     */
    @Update("UPDATE LeaveRequest SET time_slots = #{timeSlots}, reason = #{reason}, " +
            "submit_time = NOW(), status = 'pending' " +
            "WHERE student_id = #{studentId} AND job_id = #{jobId} AND leave_date = #{leaveDate}")
    int updateLeaveRequest(
            @Param("studentId") Integer studentId,
            @Param("jobId") Integer jobId,
            @Param("leaveDate") String leaveDate,
            @Param("timeSlots") String timeSlots,
            @Param("reason") String reason);



    /**
     * 获取学生指定月份的请假记录
     */
    @Select("SELECT leave_date, time_slots, reason, status " +
            "FROM LeaveRequest " +
            "WHERE student_id = #{studentId} " +
            "AND leave_date LIKE #{monthPattern}")
    List<Map<String, Object>> getMonthlyLeaveRecords(
            @Param("studentId") Integer studentId,
            @Param("monthPattern") String monthPattern);
}