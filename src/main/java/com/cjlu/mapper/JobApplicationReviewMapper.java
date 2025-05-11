package com.cjlu.mapper;

import com.cjlu.entity.JobApplicationInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface JobApplicationReviewMapper {

    /**
     * 根据申请ID查询岗位申请信息
     * 注意：使用application_id而不是id
     */
    @Select("SELECT ja.application_id as id, ja.student_id as studentId, ja.job_id as jobId, ja.status, jp.publisher_id as publisherId " +
            "FROM JobApplication ja " +
            "JOIN JobPosting jp ON ja.job_id = jp.job_id " +
            "WHERE ja.application_id = #{applicationId}")
    JobApplicationInfo getApplicationInfo(@Param("applicationId") Integer applicationId);

    /**
     * 更新申请状态
     * 注意：使用application_id而不是id
     */
    @Update("UPDATE JobApplication " +
            "SET status = #{status} " +
            "WHERE application_id = #{applicationId}")
    int updateApplicationStatus(
            @Param("applicationId") Integer applicationId,
            @Param("status") Integer status);
}