package com.cjlu.util;

import com.cjlu.dto.FundingSummaryRecordDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelUtil {

    /**
     * 生成资金发放Excel报表
     *
     * @param records 资金发放记录列表
     * @param month   月份
     * @return 生成的文件路径
     * @throws IOException 文件操作异常
     */
    public String generateFundingExcel(List<FundingSummaryRecordDTO> records, String month) throws IOException {
        // 创建工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("资金发放报表-" + month);

        // 创建标题行样式
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // 创建标题行
        Row headerRow = sheet.createRow(0);
        String[] headers = {"学号", "姓名", "学院", "总工时", "时薪(元)", "总工资(元)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 15 * 256); // 设置列宽
        }

        // 填充数据
        int rowNum = 1;
        for (FundingSummaryRecordDTO record : records) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(record.getNumber());
            row.createCell(1).setCellValue(record.getName());
            row.createCell(2).setCellValue(record.getCollege());
            row.createCell(3).setCellValue(record.getTotalHours());
            row.createCell(4).setCellValue(record.getRate());
            row.createCell(5).setCellValue(record.getTotalPay());
        }

        // 创建汇总行
        Row totalRow = sheet.createRow(rowNum);
        CellStyle totalStyle = workbook.createCellStyle();
        Font totalFont = workbook.createFont();
        totalFont.setBold(true);
        totalStyle.setFont(totalFont);

        Cell totalLabelCell = totalRow.createCell(0);
        totalLabelCell.setCellValue("合计");
        totalLabelCell.setCellStyle(totalStyle);

        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));

        // 计算总工时和总工资
        int totalHours = records.stream().mapToInt(FundingSummaryRecordDTO::getTotalHours).sum();
        int totalPay = records.stream().mapToInt(FundingSummaryRecordDTO::getTotalPay).sum();

        Cell totalHoursCell = totalRow.createCell(3);
        totalHoursCell.setCellValue(totalHours);
        totalHoursCell.setCellStyle(totalStyle);

        totalRow.createCell(4).setCellValue(""); // 时薪不需要合计

        Cell totalPayCell = totalRow.createCell(5);
        totalPayCell.setCellValue(totalPay);
        totalPayCell.setCellStyle(totalStyle);

        // 保存文件
        String fileName = "funding_" + month + ".xlsx";
        String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileName;
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }
        workbook.close();

        return fileName;
    }
}