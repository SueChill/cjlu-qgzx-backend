package com.cjlu.mapper;

import com.cjlu.dto.JobPostingDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface JobPostingMapper {

    /**
     * 插入新岗位，返回受影响的行数
     */
    @Insert("INSERT INTO JobPosting(title, description, work_time, location, phone, publisher_id, created_at, status) " +
            "VALUES(#{title}, #{description}, #{workTime}, #{location}, #{phone}, #{publisherId}, NOW(), 'pending')")
    int insertJobPosting(
            @Param("title") String title,
            @Param("description") String description,
            @Param("workTime") String workTime,
            @Param("location") String location,
            @Param("phone") String phone,
            @Param("publisherId") Integer publisherId);

    /**
     * 查询待审核的岗位列表
     */
    @Select({"<script>",
            "SELECT jp.job_id AS id, jp.title AS jobTitle, jp.description AS requirements, ",
            "jp.work_time AS time, jp.location, ",
            "CONCAT(u.name, '；', IFNULL(u.phone, '')) AS phone, ",
            "u.name AS publisher, ",
            "DATE_FORMAT(jp.created_at, '%Y-%m-%d') AS publishDate ",
            "FROM JobPosting jp ",
            "JOIN User u ON jp.publisher_id = u.id ",
            "WHERE jp.status = 'pending' ",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (jp.title LIKE CONCAT('%', #{keyword}, '%') ",
            "OR u.name LIKE CONCAT('%', #{keyword}, '%') ",
            "OR jp.location LIKE CONCAT('%', #{keyword}, '%')) ",
            "</if>",
            "ORDER BY jp.created_at DESC ",
            "LIMIT #{offset}, #{pageSize}",
            "</script>"})
    List<JobPostingDTO> getPendingPositions(
            @Param("keyword") String keyword,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize);

    /**
     * 统计待审核的岗位总数
     */
    @Select({"<script>",
            "SELECT COUNT(1) ",
            "FROM JobPosting jp ",
            "JOIN User u ON jp.publisher_id = u.id ",
            "WHERE jp.status = 'pending' ",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (jp.title LIKE CONCAT('%', #{keyword}, '%') ",
            "OR u.name LIKE CONCAT('%', #{keyword}, '%') ",
            "OR jp.location LIKE CONCAT('%', #{keyword}, '%')) ",
            "</if>",
            "</script>"})
    Integer countPendingPositions(@Param("keyword") String keyword);

    /**
     * 更新岗位审核状态
     */
    @Update("UPDATE JobPosting SET status = #{status} WHERE job_id = #{positionId}")
    int updatePositionStatus(
            @Param("positionId") Integer positionId,
            @Param("status") String status);

    /**
     * 检查岗位是否存在
     */
    @Select("SELECT COUNT(1) FROM JobPosting WHERE job_id = #{positionId}")
    int checkPositionExists(@Param("positionId") Integer positionId);

    /**
     * 查询已审核通过的岗位列表
     */
    @Select({"<script>",
            "SELECT jp.job_id AS id, jp.title AS jobTitle, jp.description AS requirements, ",
            "jp.work_time AS time, jp.location, ",
            "CONCAT(u.name, '；', IFNULL(u.phone, '')) AS phone ",
            "FROM JobPosting jp ",
            "JOIN User u ON jp.publisher_id = u.id ",
            "WHERE jp.status = 'approved' ",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (jp.title LIKE CONCAT('%', #{keyword}, '%') ",
            "OR u.name LIKE CONCAT('%', #{keyword}, '%') ",
            "OR jp.location LIKE CONCAT('%', #{keyword}, '%')) ",
            "</if>",
            "ORDER BY jp.created_at DESC ",
            "LIMIT #{offset}, #{pageSize}",
            "</script>"})
    List<JobPostingDTO> getApprovedPositions(
            @Param("keyword") String keyword,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize);

    /**
     * 统计已审核通过的岗位总数
     */
    @Select({"<script>",
            "SELECT COUNT(1) ",
            "FROM JobPosting jp ",
            "JOIN User u ON jp.publisher_id = u.id ",
            "WHERE jp.status = 'approved' ",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (jp.title LIKE CONCAT('%', #{keyword}, '%') ",
            "OR u.name LIKE CONCAT('%', #{keyword}, '%') ",
            "OR jp.location LIKE CONCAT('%', #{keyword}, '%')) ",
            "</if>",
            "</script>"})
    Integer countApprovedPositions(@Param("keyword") String keyword);
}