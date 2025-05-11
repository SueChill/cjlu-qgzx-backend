package com.cjlu.vo;

import com.cjlu.dto.StudentInfoDTO;
import lombok.Data;
import java.util.List;

/**
 * VO就是视图对象，简单理解：
 * 它是专门用于展示给用户看的数据格式
 * 就像饭店里食物的摆盘，把内容组织成好看易用的形式
 *
 * 学生列表视图对象
 * 作用：用于在前端展示学生列表数据
 */
@Data
public class StudentListVO {

    private List<StudentInfoDTO> students;
    private Integer total;
    private Integer totalPage;
    private Integer currentPage;
    private Integer pageSize;
}