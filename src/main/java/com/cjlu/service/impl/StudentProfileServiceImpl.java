package com.cjlu.service.impl;

import com.cjlu.entity.Student;
import com.cjlu.mapper.StudentProfileMapper;
import com.cjlu.service.StudentProfileService;
import org.springframework.stereotype.Service;

@Service
public class StudentProfileServiceImpl implements StudentProfileService {
    private final StudentProfileMapper studentProfileMapper;

    public StudentProfileServiceImpl(StudentProfileMapper studentProfileMapper) {
        this.studentProfileMapper = studentProfileMapper;
    }

    @Override
    public Student getStudentProfile(Integer studentId) {
        return studentProfileMapper.getStudentProfile(studentId);
    }
}