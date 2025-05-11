package com.cjlu.mapper;

import com.cjlu.dto.AppliedPositionDTO;
import com.cjlu.entity.JobApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface JobApplicationMapper {
    int insertApplication(JobApplication application);

    List<AppliedPositionDTO> getApplicationsByStudentId(Integer studentId);

    /**
     * 查询学生已录用的岗位ID
     * 状态1表示已录用
     */
    @Select("SELECT job_id FROM JobApplication WHERE student_id = #{studentId} AND status = 1")
    List<Integer> getApprovedJobIdsByStudentId(@Param("studentId") Integer studentId);

    /**
     * 获取学生已录用岗位的详细信息
     */
    @Select("SELECT ja.job_id as jobId, jp.title as jobTitle, jp.location, jp.work_time as workTime " +
            "FROM JobApplication ja " +
            "JOIN JobPosting jp ON ja.job_id = jp.job_id " +
            "WHERE ja.student_id = #{studentId} AND ja.status = 1")
    List<Map<String, Object>> getApprovedJobDetailsForStudent(@Param("studentId") Integer studentId);


}