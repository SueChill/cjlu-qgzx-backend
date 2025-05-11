package com.cjlu.service;

import com.cjlu.dto.StudentLeaveDTO;

/**
 * 学生请假服务接口
 */
public interface StudentLeaveService {

    /**
     * 提交请假申请
     * @param leaveDTO 请假申请数据
     * @param jobId 岗位ID
     * @return 是否提交成功
     */
    boolean submitLeaveRequest(StudentLeaveDTO leaveDTO, Integer jobId);
}