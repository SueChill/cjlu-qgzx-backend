package com.cjlu.mapper;

import com.cjlu.dto.MonthlyDutySummaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentDutyMapper {

    /**
     * 查询学生本月出勤记录
     */
    @Select({"<script>",
            "SELECT DATE_FORMAT(a.sign_in_time, '%Y-%m-%d') as date, ",
            "CONCAT(DATE_FORMAT(a.sign_in_time, '%H:%i'), '-', DATE_FORMAT(a.sign_out_time, '%H:%i')) as time_slot ",
            "FROM Attendance a ",
            "WHERE a.student_id = #{studentId} ",
            "<if test='month != null'>",
            "AND DATE_FORMAT(a.sign_in_time, '%Y-%m') = #{month} ",
            "</if>",
            "ORDER BY a.sign_in_time",
            "</script>"})
    List<Map<String, String>> getAttendanceRecords(
            @Param("studentId") Integer studentId,
            @Param("month") String month);

    /**
     * 查询学生本月提交的工时记录
     */
    @Select({"<script>",
            "SELECT DATE_FORMAT(dh.work_date, '%Y-%m-%d') as date, ",
            "dh.time_slots as time_slots, ",
            "dh.status ",
            "FROM DailyHours dh ",
            "WHERE dh.student_id = #{studentId} ",
            "<if test='month != null'>",
            "AND DATE_FORMAT(dh.work_date, '%Y-%m') = #{month} ",
            "</if>",
            "ORDER BY dh.work_date, dh.submit_time",
            "</script>"})
    List<Map<String, Object>> getDailyHoursRecords(
            @Param("studentId") Integer studentId,
            @Param("month") String month);

    /**
     * 查询学生本月请假记录 (修改版)
     */
    @Select({"<script>",
            "SELECT DATE_FORMAT(l.leave_date, '%Y-%m-%d') as date, ",
            "l.reason, ",
            "l.leave_time as time ",
            "FROM LeaveApplication l ",
            "WHERE l.student_id = #{studentId} ",
            "<if test='month != null'>",
            "AND DATE_FORMAT(l.leave_date, '%Y-%m') = #{month} ",
            "</if>",
            "ORDER BY l.leave_date",
            "</script>"})
    List<Map<String, String>> getLeaveRecords(
            @Param("studentId") Integer studentId,
            @Param("month") String month);

    /**
     * 查询学生本月累计工时
     */
    @Select({"<script>",
            "SELECT IFNULL(SUM(dh.hours), 0) as total_hours ",
            "FROM DailyHours dh ",
            "WHERE dh.student_id = #{studentId} ",
            "<if test='month != null'>",
            "AND DATE_FORMAT(dh.work_date, '%Y-%m') = #{month} ",
            "</if>",
            "AND dh.status = 'approved'",
            "</script>"})
    float getTotalApprovedHours(
            @Param("studentId") Integer studentId,
            @Param("month") String month);








}