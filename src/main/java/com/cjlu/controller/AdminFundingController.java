package com.cjlu.controller;

import com.cjlu.dto.FileDownloadDTO;
import com.cjlu.dto.FundingExportRequestDTO;
import com.cjlu.dto.FundingSummaryQueryDTO;
import com.cjlu.entity.Result;
import com.cjlu.service.FundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理员资金发放Controller
 */
@RestController
@RequestMapping("/api/admin/funding")
public class AdminFundingController {

    @Autowired
    private FundingService fundingService;

    /**
     * 获取资金发放汇总信息
     */
    @GetMapping("/summary")
    public Result getFundingSummary(@RequestBody(required = false) FundingSummaryQueryDTO queryDTO) {
        try {
            if (queryDTO == null) {
                queryDTO = new FundingSummaryQueryDTO();
            }

            Map<String, Object> data = fundingService.getFundingSummary(queryDTO);

            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取资金发放汇总信息失败: " + e.getMessage());
        }
    }

    /**
     * 导出资金发放报表
     */
    @PostMapping("/export")
    public Result exportFundingReport(@RequestBody FundingExportRequestDTO exportRequestDTO) {
        try {
            if (exportRequestDTO == null || exportRequestDTO.getMonth() == null || exportRequestDTO.getMonth().isEmpty()) {
                return Result.error("请指定导出月份");
            }

            FileDownloadDTO fileDownloadDTO = fundingService.exportFundingReport(exportRequestDTO);

            if ("no_data".equals(fileDownloadDTO.getFileUrl())) {
                return Result.error("所选月份没有资金发放数据");
            }

            return Result.success(fileDownloadDTO);
        } catch (Exception e) {
            return Result.error("导出资金发放报表失败: " + e.getMessage());
        }
    }
}