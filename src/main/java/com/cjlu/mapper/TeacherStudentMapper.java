package com.cjlu.mapper;

import com.cjlu.dto.StudentInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教师查看学生信息Mapper
 */
@Mapper
public interface TeacherStudentMapper {

    /**
     * 查询教师关联的学生信息（分页）
     * @param teacherId 教师ID
     * @param jobId 岗位ID（可选）
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 学生信息列表
     */
    List<StudentInfoDTO> selectStudentsByTeacher(
            @Param("teacherId") Integer teacherId,
            @Param("jobId") Integer jobId,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit);

    /**
     * 查询教师关联的学生总数
     * @param teacherId 教师ID
     * @param jobId 岗位ID（可选）
     * @return 学生总数
     */
    Integer countStudentsByTeacher(
            @Param("teacherId") Integer teacherId,
            @Param("jobId") Integer jobId);

    /**
     * 查询学生详细信息
     * @param studentId 学生ID
     * @return 学生详细信息
     */
    StudentInfoDTO selectStudentDetail(@Param("studentId") Integer studentId);

    /**
     * 检查学生是否属于教师的岗位
     * @param studentId 学生ID
     * @param teacherId 教师ID
     * @return 是否关联
     */
    Boolean checkStudentTeacherRelation(
            @Param("studentId") Integer studentId,
            @Param("teacherId") Integer teacherId);

    /**
     * 查询教师所有岗位
     * @param teacherId 教师ID
     * @return 岗位ID列表
     */
    List<Integer> selectTeacherJobIds(@Param("teacherId") Integer teacherId);
}