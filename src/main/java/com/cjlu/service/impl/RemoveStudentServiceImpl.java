package com.cjlu.service.impl;

import com.cjlu.mapper.RemoveStudentMapper;
import com.cjlu.service.RemoveStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 移除学生Service实现
 */
@Service
public class RemoveStudentServiceImpl implements RemoveStudentService {

    @Autowired
    private RemoveStudentMapper removeStudentMapper;

    @Override
    @Transactional
    public boolean removeStudent(Integer studentId, Integer teacherId) {
        // 检查学生是否存在
        Map<String, Object> studentInfo = removeStudentMapper.getStudentInfo(studentId);
        if (studentInfo == null) {
            throw new RuntimeException("学生不存在");
        }

        // 检查学生是否属于该教师的岗位
        Boolean hasRelation = removeStudentMapper.checkStudentTeacherRelation(studentId, teacherId);
        if (Boolean.FALSE.equals(hasRelation)) {
            throw new RuntimeException("该学生不属于您管理的岗位");
        }

        // 获取学生所在的该教师的岗位ID列表
        List<Integer> jobIds = removeStudentMapper.getStudentJobIds(studentId, teacherId);

        if (jobIds.isEmpty()) {
            throw new RuntimeException("未找到学生与岗位的关联关系");
        }

        // 移除学生与所有岗位的关联
        int successCount = 0;
        for (Integer jobId : jobIds) {
            int result = removeStudentMapper.removeStudentFromJob(studentId, jobId);
            successCount += result;
        }

        // 如果至少有一个岗位关联被成功移除，则视为操作成功
        return successCount > 0;
    }
}