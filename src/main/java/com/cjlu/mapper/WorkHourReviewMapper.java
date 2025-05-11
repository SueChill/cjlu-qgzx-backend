package com.cjlu.mapper;

import com.cjlu.dto.WorkHourRecordDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WorkHourReviewMapper {

    /**
     * 获取教师名下学生待审核工时记录
     */
    @Select({"<script>",
            "SELECT dh.record_id, dh.record_code as recordId, dh.student_id as studentId, u.name as studentName, ",
            "DATE_FORMAT(dh.work_date, '%Y-%m-%d') as date, dh.time_slots as timeSlots, ",
            "dh.status, dh.hours as totalHours ",
            "FROM DailyHours dh ",
            "JOIN JobPosting jp ON dh.job_id = jp.job_id ",
            "JOIN User u ON dh.student_id = u.id ",
            "WHERE jp.publisher_id = #{teacherId} ",
            "<if test='month != null'>",
            "AND DATE_FORMAT(dh.work_date, '%Y-%m') = #{month} ",
            "</if>",
            "AND dh.status = 'pending' ",
            "ORDER BY dh.work_date DESC, dh.submit_time DESC ",
            "LIMIT #{offset}, #{pageSize}",
            "</script>"})
    List<WorkHourRecordDTO> getPendingWorkHours(
            @Param("teacherId") Integer teacherId,
            @Param("month") String month,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize);

    /**
     * 获取待审核工时记录总数
     */
    @Select({"<script>",
            "SELECT COUNT(1) ",
            "FROM DailyHours dh ",
            "JOIN JobPosting jp ON dh.job_id = jp.job_id ",
            "WHERE jp.publisher_id = #{teacherId} ",
            "<if test='month != null'>",
            "AND DATE_FORMAT(dh.work_date, '%Y-%m') = #{month} ",
            "</if>",
            "AND dh.status = 'pending'",
            "</script>"})
    int countPendingWorkHours(
            @Param("teacherId") Integer teacherId,
            @Param("month") String month);

    /**
     * 更新工时记录状态
     */
    @Update("UPDATE DailyHours " +
            "SET status = #{status}, reviewer_id = #{reviewerId}, review_time = NOW() " +
            "WHERE record_code = #{recordId} " +
            "AND status = 'pending'")
    int updateWorkHourStatus(
            @Param("recordId") String recordId,
            @Param("status") String status,
            @Param("reviewerId") Integer reviewerId);

    /**
     * 检查工时记录是否归属于该教师
     */
    @Select("SELECT COUNT(1) FROM DailyHours dh " +
            "JOIN JobPosting jp ON dh.job_id = jp.job_id " +
            "WHERE dh.record_code = #{recordId} " +
            "AND jp.publisher_id = #{teacherId}")
    int checkWorkHourBelongsToTeacher(
            @Param("recordId") String recordId,
            @Param("teacherId") Integer teacherId);
}