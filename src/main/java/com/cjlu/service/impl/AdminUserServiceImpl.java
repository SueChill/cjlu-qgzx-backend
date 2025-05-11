package com.cjlu.service.impl;

import com.cjlu.dto.TeacherCreateDTO;
import com.cjlu.dto.UserInfoDTO;
import com.cjlu.dto.UserQueryDTO;
import com.cjlu.mapper.AdminUserMapper;
import com.cjlu.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public Map<String, Object> getUserList(UserQueryDTO queryDTO) {
        // 计算分页偏移量
        int pageSize = queryDTO.getPageSize();
        int offset = (queryDTO.getPage() - 1) * pageSize;

        // 查询用户列表
        List<UserInfoDTO> users = adminUserMapper.getUserList(
                queryDTO.getRole(), queryDTO.getKeyword(), offset, pageSize);

        // 查询总记录数
        Integer total = adminUserMapper.countUsers(queryDTO.getRole(), queryDTO.getKeyword());

        // 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("users", users);

        return result;
    }

    @Override
    @Transactional
    public UserInfoDTO createTeacher(TeacherCreateDTO teacherDTO) {
        // 参数校验
        validateTeacherData(teacherDTO);

        // 密码加密（使用MD5加密）
        String encryptedPassword = DigestUtils
                .md5DigestAsHex(teacherDTO.getPassword().getBytes(StandardCharsets.UTF_8));
        teacherDTO.setPassword(encryptedPassword);

        // 创建教师用户
        adminUserMapper.createTeacher(teacherDTO);
        Integer teacherId = teacherDTO.getId();

        // 创建教师表记录
        adminUserMapper.createTeacherProfile(teacherId);

        // 返回创建的用户信息
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setId(teacherId);
        userInfo.setName(teacherDTO.getName());
        userInfo.setCollege(teacherDTO.getCollege());
        userInfo.setNumber(teacherDTO.getNumber());
        userInfo.setPhone(teacherDTO.getPhone());
        userInfo.setRole("teacher");

        return userInfo;
    }

    /**
     * 验证教师数据合法性
     */
    private void validateTeacherData(TeacherCreateDTO teacherDTO) {
        // 检查必填字段
        if (teacherDTO.getName() == null || teacherDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("姓名不能为空");
        }

        if (teacherDTO.getCollege() == null || teacherDTO.getCollege().trim().isEmpty()) {
            throw new IllegalArgumentException("院系不能为空");
        }

        if (teacherDTO.getNumber() == null || teacherDTO.getNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("工号不能为空");
        }

        if (teacherDTO.getPhone() == null || teacherDTO.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("手机号不能为空");
        }

        if (teacherDTO.getPassword() == null || teacherDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }

        // 检查手机号格式
        if (!teacherDTO.getPhone().matches("^1[3-9]\\d{9}$")) {
            throw new IllegalArgumentException("手机号格式不正确");
        }

        // 检查工号和手机号是否已存在
        if (adminUserMapper.checkNumberExists(teacherDTO.getNumber()) > 0) {
            throw new IllegalArgumentException("该工号已存在");
        }

        if (adminUserMapper.checkPhoneExists(teacherDTO.getPhone()) > 0) {
            throw new IllegalArgumentException("该手机号已存在");
        }
    }
}