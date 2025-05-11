package com.cjlu.service;

/**
 * 移除学生Service
 */
public interface RemoveStudentService {

    /**
     * 移除学生与岗位的关联
     * @param studentId 学生ID
     * @param teacherId 教师ID
     * @return 是否成功
     */
    boolean removeStudent(Integer studentId, Integer teacherId);
}