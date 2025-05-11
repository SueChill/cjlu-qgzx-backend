package com.cjlu.service.impl;

import com.cjlu.entity.JobApplicationInfo;
import com.cjlu.mapper.JobApplicationReviewMapper;
import com.cjlu.service.JobApplicationReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobApplicationReviewServiceImpl implements JobApplicationReviewService {

    @Autowired
    private JobApplicationReviewMapper jobApplicationReviewMapper;

    @Override
    @Transactional
    public boolean reviewApplication(Integer applicationId, Integer teacherId, String status) {
        // 参数校验
        if (status == null || (!status.equals("approved") && !status.equals("rejected"))) {
            throw new IllegalArgumentException("审核状态无效，必须为approved或rejected");
        }

        // 获取申请信息
        JobApplicationInfo applicationInfo = jobApplicationReviewMapper.getApplicationInfo(applicationId);

        // 检查申请是否存在
        if (applicationInfo == null) {
            throw new RuntimeException("申请记录不存在");
        }

        // 验证当前教师是否有权限审核该申请
        if (!applicationInfo.getPublisherId().equals(teacherId)) {
            throw new RuntimeException("您无权审核此申请");
        }

        // 检查申请是否已经被审核
        if (applicationInfo.getStatus() != 0) {
            throw new RuntimeException("该申请已经被审核过");
        }

        // 将审核结果转换为数据库中的状态值
        int newStatus;
        if (status.equals("approved")) {
            newStatus = 1;  // 同意/已录用
        } else {
            newStatus = 2;  // 拒绝
        }

        // 更新申请状态
        int result = jobApplicationReviewMapper.updateApplicationStatus(applicationId, newStatus);

        return result > 0;
    }
}