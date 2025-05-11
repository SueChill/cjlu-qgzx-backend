package com.cjlu.service;

/**
 * 岗位申请审核Service
 */
public interface JobApplicationReviewService {

    /**
     * 审核学生的岗位申请
     * @param applicationId 申请ID
     * @param teacherId 教师ID
     * @param status 审核结果
     * @return 是否成功
     */
    boolean reviewApplication(Integer applicationId, Integer teacherId, String status);
}