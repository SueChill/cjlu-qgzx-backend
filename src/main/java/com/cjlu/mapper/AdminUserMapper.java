package com.cjlu.mapper;

import com.cjlu.dto.TeacherCreateDTO;
import com.cjlu.dto.UserInfoDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminUserMapper {

    /**
     * 查询用户列表
     */
    @Select({"<script>",
            "SELECT id, name, college, number, phone, role ",
            "FROM User ",
            "WHERE 1=1 ",
            "<if test='role != null and role != \"\"'>",
            "AND role = #{role} ",
            "</if>",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (name LIKE CONCAT('%', #{keyword}, '%') ",
            "OR number LIKE CONCAT('%', #{keyword}, '%') ",
            "OR college LIKE CONCAT('%', #{keyword}, '%')) ",
            "</if>",
            "ORDER BY id DESC ",
            "LIMIT #{offset}, #{pageSize}",
            "</script>"})
    List<UserInfoDTO> getUserList(
            @Param("role") String role,
            @Param("keyword") String keyword,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize);

    /**
     * 查询用户总数
     */
    @Select({"<script>",
            "SELECT COUNT(1) ",
            "FROM User ",
            "WHERE 1=1 ",
            "<if test='role != null and role != \"\"'>",
            "AND role = #{role} ",
            "</if>",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (name LIKE CONCAT('%', #{keyword}, '%') ",
            "OR number LIKE CONCAT('%', #{keyword}, '%') ",
            "OR college LIKE CONCAT('%', #{keyword}, '%')) ",
            "</if>",
            "</script>"})
    Integer countUsers(
            @Param("role") String role,
            @Param("keyword") String keyword);

    /**
     * 检查工号是否已存在
     */
    @Select("SELECT COUNT(1) FROM User WHERE number = #{number}")
    Integer checkNumberExists(String number);

    /**
     * 检查手机号是否已存在
     */
    @Select("SELECT COUNT(1) FROM User WHERE phone = #{phone}")
    Integer checkPhoneExists(String phone);

    /**
     * 创建教师用户
     */
    @Insert("INSERT INTO User (name, college, number, password, phone, role) " +
            "VALUES (#{name}, #{college}, #{number}, #{password}, #{phone}, 'teacher')")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer createTeacher(TeacherCreateDTO teacherDTO);

    /**
     * 创建教师表记录
     */
    @Insert("INSERT INTO Teacher (teacher_id) VALUES (#{teacherId})")
    Integer createTeacherProfile(Integer teacherId);
}