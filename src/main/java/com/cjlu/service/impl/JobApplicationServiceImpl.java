package com.cjlu.service.impl;

import com.cjlu.dto.AppliedPositionDTO;
import com.cjlu.dto.JobApplicationDTO;
import com.cjlu.entity.JobApplication;
import com.cjlu.mapper.JobApplicationMapper;
import com.cjlu.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationMapper jobApplicationMapper;

    @Override
    public List<AppliedPositionDTO> getAppliedPositions(Integer studentId) {
        List<AppliedPositionDTO> list = jobApplicationMapper.getApplicationsByStudentId(studentId);
        // 转换状态码为文字描述
        list.forEach(dto -> {
            dto.setStatus(convertStatus(Integer.valueOf(dto.getApplicationStatus())));
        });
        return list;
    }

    private String convertStatus(Integer code) {
        switch(code) {
            case 0: return "申请中";
            case 1: return "已通过";
            case 2: return "未通过";
            case 3: return "调剂中";
            default: return "未知状态";
        }
    }

    @Override
    public boolean createApplication(JobApplicationDTO dto) {
        JobApplication application = new JobApplication();
        application.setStudentId(dto.getStudentId());
        application.setJobId(dto.getJobId());
        application.setAvailTimes(dto.getAvailTimes());
        application.setReason(dto.getReason());
        application.setStatus(0);  // 默认状态为0

        return jobApplicationMapper.insertApplication(application) > 0;
    }
}
