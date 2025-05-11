package com.cjlu.service.impl;

import com.cjlu.mapper.TeacherStudentMapper;
import com.cjlu.dto.StudentInfoDTO;
import com.cjlu.vo.StudentListVO;
import com.cjlu.service.TeacherStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师查看学生信息Service实现类
 */
@Service
@Slf4j
public class TeacherStudentServiceImpl implements TeacherStudentService {

    @Autowired
    private TeacherStudentMapper teacherStudentMapper;

    @Override
    public StudentListVO getStudentsByTeacher(Integer teacherId, Integer jobId, Integer page, Integer size) {
        // 默认值处理
        page = page == null || page < 1 ? 1 : page;
        size = size == null || size < 1 ? 10 : size;

        // 计算分页参数
        Integer offset = (page - 1) * size;

        // 查询总数
        Integer total = teacherStudentMapper.countStudentsByTeacher(teacherId, jobId);

        // 计算总页数
        Integer totalPage = (total + size - 1) / size;

        // 查询数据
        List<StudentInfoDTO> students = teacherStudentMapper.selectStudentsByTeacher(teacherId, jobId, offset, size);

        // 封装结果
        StudentListVO result = new StudentListVO();
        result.setStudents(students);
        result.setTotal(total);
        result.setTotalPage(totalPage);
        result.setCurrentPage(page);
        result.setPageSize(size);

        return result;
    }

    @Override
    public StudentInfoDTO getStudentDetail(Integer studentId, Integer teacherId) {
        // 检查权限（学生是否属于该教师的岗位）
        Boolean hasRelation = teacherStudentMapper.checkStudentTeacherRelation(studentId, teacherId);
        if (!hasRelation) {
            log.warn("教师[{}]尝试查看非关联学生[{}]的信息", teacherId, studentId);
            throw new RuntimeException("无权查看该学生信息");
        }

        // 查询学生详情
        return teacherStudentMapper.selectStudentDetail(studentId);
    }
}