package com.cjlu.service;

import com.cjlu.entity.Student;

/**
 * 学生页面信息自获取
 */
public interface StudentProfileService {
    /**
     *
     * @param studentId
     * @return
     */
    Student getStudentProfile(Integer studentId);
}