package com.cjlu.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间工具类
 */
public class DateTimeUtil {

    /**
     * 生成工时记录编号：WH + 日期 + 3位序号
     */
    public static String generateWorkHourRecordId(int sequence) {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return String.format("WH%s%03d", dateStr, sequence);
    }
}