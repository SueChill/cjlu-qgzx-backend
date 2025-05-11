package com.cjlu.service;

import com.cjlu.dto.TeacherCreateDTO;
import com.cjlu.dto.UserInfoDTO;
import com.cjlu.dto.UserQueryDTO;
import java.util.Map;

/**
 * 管理员用户服务
 */
public interface AdminUserService {

    /**
     * 获取用户列表
     * @param queryDTO 查询参数
     * @return 用户列表和总数
     */
    Map<String, Object> getUserList(UserQueryDTO queryDTO);

    /**
     * 创建教师用户
     * @param teacherDTO 教师信息
     * @return 创建的教师用户信息
     */
    UserInfoDTO createTeacher(TeacherCreateDTO teacherDTO);
}