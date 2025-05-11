package com.cjlu.service;

import com.cjlu.dto.AppliedPositionDTO;
import com.cjlu.dto.JobApplicationDTO;

import java.util.List;

public interface JobApplicationService {
    //岗位申请功能

    /**
     *
     * @param dto
     * @return
     */
    public boolean createApplication(JobApplicationDTO dto);

    //岗位查询功能

    /**
     *
     * @param studentId
     * @return
     */
    public List<AppliedPositionDTO> getAppliedPositions(Integer studentId);
}
