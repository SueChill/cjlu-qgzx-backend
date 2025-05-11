package com.cjlu.service;

import com.cjlu.dto.FileDownloadDTO;
import com.cjlu.dto.FundingExportRequestDTO;
import com.cjlu.dto.FundingSummaryQueryDTO;

import java.util.Map;

public interface FundingService {
    /**
     * 获取资金发放汇总信息
     */
    Map<String, Object> getFundingSummary(FundingSummaryQueryDTO queryDTO);

    /**
     * 导出资金发放报表
     */
    FileDownloadDTO exportFundingReport(FundingExportRequestDTO exportRequestDTO);
}