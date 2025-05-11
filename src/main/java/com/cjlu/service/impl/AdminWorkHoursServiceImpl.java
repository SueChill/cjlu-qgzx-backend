package com.cjlu.service.impl;

import com.cjlu.dto.WorkHoursQueryDTO;
import com.cjlu.dto.WorkHoursRecordDTO;
import com.cjlu.mapper.AdminWorkHoursMapper;
import com.cjlu.service.AdminWorkHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminWorkHoursServiceImpl implements AdminWorkHoursService {

    @Autowired
    private AdminWorkHoursMapper adminWorkHoursMapper;

    @Override
    public Map<String, Object> getStudentWorkHours(WorkHoursQueryDTO queryDTO) {
        // 计算分页偏移量
        int pageSize = queryDTO.getPageSize();
        int offset = (queryDTO.getPage() - 1) * pageSize;

        // 查询工时记录
        List<WorkHoursRecordDTO> records = adminWorkHoursMapper.getWorkHoursRecords(
                queryDTO.getDateFrom(),
                queryDTO.getDateTo(),
                queryDTO.getKeyword(),
                offset,
                pageSize);

        // 查询总记录数
        Integer total = adminWorkHoursMapper.countWorkHoursRecords(
                queryDTO.getDateFrom(),
                queryDTO.getDateTo(),
                queryDTO.getKeyword());

        // 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("records", records);

        return result;
    }
}