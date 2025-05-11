package com.cjlu.mapper;

import com.cjlu.dto.WorkHoursRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminWorkHoursMapper {

    /**
     * 查询学生工时记录
     */
    @Select({"<script>",
            "SELECT dh.record_id as id, dh.student_id as studentId, ",
            "u.name as name, u.number as number, ",
            "DATE_FORMAT(dh.work_date, '%Y-%m-%d') as date, ",
            "dh.hours as hours ",
            "FROM DailyHours dh ",
            "JOIN User u ON dh.student_id = u.id ",
            "WHERE dh.status = 'approved' ",
            "<if test='dateFrom != null and dateFrom != \"\"'>",
            "AND dh.work_date &gt;= #{dateFrom} ",
            "</if>",
            "<if test='dateTo != null and dateTo != \"\"'>",
            "AND dh.work_date &lt;= #{dateTo} ",
            "</if>",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (u.name LIKE CONCAT('%', #{keyword}, '%') ",
            "OR u.number LIKE CONCAT('%', #{keyword}, '%')) ",
            "</if>",
            "ORDER BY dh.work_date DESC, dh.student_id ",
            "LIMIT #{offset}, #{pageSize}",
            "</script>"})
    List<WorkHoursRecordDTO> getWorkHoursRecords(
            @Param("dateFrom") String dateFrom,
            @Param("dateTo") String dateTo,
            @Param("keyword") String keyword,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize);

    /**
     * 查询符合条件的工时记录总数
     */
    @Select({"<script>",
            "SELECT COUNT(1) ",
            "FROM DailyHours dh ",
            "JOIN User u ON dh.student_id = u.id ",
            "WHERE dh.status = 'approved' ",
            "<if test='dateFrom != null and dateFrom != \"\"'>",
            "AND dh.work_date &gt;= #{dateFrom} ",
            "</if>",
            "<if test='dateTo != null and dateTo != \"\"'>",
            "AND dh.work_date &lt;= #{dateTo} ",
            "</if>",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (u.name LIKE CONCAT('%', #{keyword}, '%') ",
            "OR u.number LIKE CONCAT('%', #{keyword}, '%')) ",
            "</if>",
            "</script>"})
    Integer countWorkHoursRecords(
            @Param("dateFrom") String dateFrom,
            @Param("dateTo") String dateTo,
            @Param("keyword") String keyword);
}