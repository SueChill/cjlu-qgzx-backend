package com.cjlu.service.impl;

import com.cjlu.dto.FileDownloadDTO;
import com.cjlu.dto.FundingExportRequestDTO;
import com.cjlu.dto.FundingSummaryQueryDTO;
import com.cjlu.dto.FundingSummaryRecordDTO;
import com.cjlu.mapper.FundingMapper;
import com.cjlu.service.FundingService;
import com.cjlu.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FundingServiceImpl implements FundingService {

    @Autowired
    private FundingMapper fundingMapper;

    @Autowired
    private ExcelUtil excelUtil;

    @Value("${file.download.base-url}")
    private String fileDownloadBaseUrl;

    @Override
    public Map<String, Object> getFundingSummary(FundingSummaryQueryDTO queryDTO) {
        // 计算分页偏移量
        int pageSize = queryDTO.getPageSize();
        int offset = (queryDTO.getPage() - 1) * pageSize;

        // 查询资金发放汇总信息
        List<FundingSummaryRecordDTO> records = fundingMapper.getFundingSummary(
                queryDTO.getKeyword(),
                queryDTO.getCollege(),
                queryDTO.getMonth(),
                offset,
                pageSize);

        // 查询总记录数
        Integer total = fundingMapper.countFundingSummary(
                queryDTO.getKeyword(),
                queryDTO.getCollege(),
                queryDTO.getMonth());

        // 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("records", records);

        return result;
    }

    @Override
    public FileDownloadDTO exportFundingReport(FundingExportRequestDTO exportRequestDTO) {
        try {
            // 获取指定月份的所有资金记录
            List<FundingSummaryRecordDTO> records = fundingMapper.getAllFundingRecordsForMonth(
                    exportRequestDTO.getMonth());

            if (records == null || records.isEmpty()) {
                return new FileDownloadDTO("no_data");
            }

            // 生成Excel文件
            String fileName = excelUtil.generateFundingExcel(records, exportRequestDTO.getMonth());

            // 构建文件下载URL
            String fileUrl = fileDownloadBaseUrl + "/download/" + fileName;

            return new FileDownloadDTO(fileUrl);
        } catch (IOException e) {
            throw new RuntimeException("导出资金发放报表失败: " + e.getMessage(), e);
        }
    }
}