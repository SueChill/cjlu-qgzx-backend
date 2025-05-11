package com.cjlu.vo;

import com.cjlu.dto.LeaveRecordDTO;
import lombok.Data;
import java.util.List;

/**
 * 请假记录分页结果
 */
@Data
public class LeaveRecordVO {
    private List<LeaveRecordDTO> records;  // 请假记录
    private Integer total;                // 总记录数
    private Integer totalPage;            // 总页数
    private Integer currentPage;          // 当前页
    private Integer pageSize;             // 每页大小
}