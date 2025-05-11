package com.cjlu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件下载DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadDTO {
    private String fileUrl;  // 文件下载链接
}