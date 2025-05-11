package com.cjlu.mapper;

import com.cjlu.dto.AdminEvaluationResultDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminEvaluationMapper {

    /**
     * 查询学生评价列表（使用单月份查询）
     */
    @Select({"<script>",
            "SELECT se.evaluation_id AS id, se.student_id AS studentId, su.name AS studentName, su.number AS studentNumber, ",
            "se.teacher_id AS teacherId, tu.name AS teacherName, ",
            "se.job_id AS jobId, jp.title AS jobTitle, ",
            "se.month, se.comment, se.evaluation_time AS evaluationTime ",
            "FROM StudentEvaluation se ",
            "JOIN User su ON se.student_id = su.id ",
            "JOIN User tu ON se.teacher_id = tu.id ",
            "LEFT JOIN JobPosting jp ON se.job_id = jp.job_id ",
            "WHERE 1=1 ",
            "<if test='student != null and student != \"\"'>",
            "AND (su.name LIKE CONCAT('%', #{student}, '%') OR su.number LIKE CONCAT('%', #{student}, '%')) ",
            "</if>",
            "<if test='teacher != null and teacher != \"\"'>",
            "AND tu.name LIKE CONCAT('%', #{teacher}, '%') ",
            "</if>",
            "<if test='month != null and month != \"\"'>",
            "AND se.month = #{month} ",
            "</if>",
            "ORDER BY se.month DESC, se.evaluation_id DESC ",
            "LIMIT #{offset}, #{pageSize}",
            "</script>"})
    List<AdminEvaluationResultDTO> getEvaluations(
            @Param("student") String student,
            @Param("teacher") String teacher,
            @Param("month") String month,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize);

    /**
     * 统计符合条件的评价总数
     */
    @Select({"<script>",
            "SELECT COUNT(1) ",
            "FROM StudentEvaluation se ",
            "JOIN User su ON se.student_id = su.id ",
            "JOIN User tu ON se.teacher_id = tu.id ",
            "LEFT JOIN JobPosting jp ON se.job_id = jp.job_id ",
            "WHERE 1=1 ",
            "<if test='student != null and student != \"\"'>",
            "AND (su.name LIKE CONCAT('%', #{student}, '%') OR su.number LIKE CONCAT('%', #{student}, '%')) ",
            "</if>",
            "<if test='teacher != null and teacher != \"\"'>",
            "AND tu.name LIKE CONCAT('%', #{teacher}, '%') ",
            "</if>",
            "<if test='month != null and month != \"\"'>",
            "AND se.month = #{month} ",
            "</if>",
            "</script>"})
    Integer countEvaluations(
            @Param("student") String student,
            @Param("teacher") String teacher,
            @Param("month") String month);
}