package com.cjlu.mapper;

import com.cjlu.dto.FundingSummaryRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FundingMapper {

    /**
     * 查询学生工时与资金统计信息
     * 基于学生提交的并已审核通过的工时记录
     */
    @Select({"<script>",
            "SELECT s.student_id AS studentId, u.name, u.college, u.number, ",
            "SUM(d.hours) AS totalHours, 24 AS rate, ",
            "SUM(d.hours) * 24 AS totalPay, ",
            "DATE_FORMAT(d.work_date, '%Y-%m') AS month ",
            "FROM DailyHours d ",
            "JOIN student s ON d.student_id = s.student_id ",
            "JOIN User u ON s.student_id = u.id ",
            "WHERE d.status = 'approved' ",
            "<if test='month != null and month != \"\"'>",
            "AND DATE_FORMAT(d.work_date, '%Y-%m') = #{month} ",
            "</if>",
            "<if test='college != null and college != \"\"'>",
            "AND u.college = #{college} ",
            "</if>",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (u.name LIKE CONCAT('%', #{keyword}, '%') ",
            "OR u.number LIKE CONCAT('%', #{keyword}, '%')) ",
            "</if>",
            "GROUP BY s.student_id, u.name, u.college, u.number, DATE_FORMAT(d.work_date, '%Y-%m') ",
            "ORDER BY u.college, u.name ",
            "LIMIT #{offset}, #{pageSize}",
            "</script>"})
    List<FundingSummaryRecordDTO> getFundingSummary(
            @Param("keyword") String keyword,
            @Param("college") String college,
            @Param("month") String month,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize);

    /**
     * 统计符合条件的记录总数
     */
    @Select({"<script>",
            "SELECT COUNT(DISTINCT CONCAT(s.student_id, DATE_FORMAT(d.work_date, '%Y-%m'))) ",
            "FROM DailyHours d ",
            "JOIN student s ON d.student_id = s.student_id ",
            "JOIN User u ON s.student_id = u.id ",
            "WHERE d.status = 'approved' ",
            "<if test='month != null and month != \"\"'>",
            "AND DATE_FORMAT(d.work_date, '%Y-%m') = #{month} ",
            "</if>",
            "<if test='college != null and college != \"\"'>",
            "AND u.college = #{college} ",
            "</if>",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (u.name LIKE CONCAT('%', #{keyword}, '%') ",
            "OR u.number LIKE CONCAT('%', #{keyword}, '%')) ",
            "</if>",
            "</script>"})
    Integer countFundingSummary(
            @Param("keyword") String keyword,
            @Param("college") String college,
            @Param("month") String month);

    /**
     * 获取指定月份的所有资金统计信息（用于导出）
     */
    @Select("SELECT s.student_id AS studentId, u.name, u.college, u.number, " +
            "SUM(d.hours) AS totalHours, 24 AS rate, " +
            "SUM(d.hours) * 24 AS totalPay, " +
            "DATE_FORMAT(d.work_date, '%Y-%m') AS month " +
            "FROM DailyHours d " +
            "JOIN student s ON d.student_id = s.student_id " +
            "JOIN User u ON s.student_id = u.id " +
            "WHERE d.status = 'approved' " +
            "AND DATE_FORMAT(d.work_date, '%Y-%m') = #{month} " +
            "GROUP BY s.student_id, u.name, u.college, u.number, DATE_FORMAT(d.work_date, '%Y-%m') " +
            "ORDER BY u.college, u.name")
    List<FundingSummaryRecordDTO> getAllFundingRecordsForMonth(@Param("month") String month);
}