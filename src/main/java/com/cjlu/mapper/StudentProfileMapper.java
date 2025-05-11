// StudentProfileMapper.java
package com.cjlu.mapper;

import com.cjlu.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StudentProfileMapper {
    Student getStudentProfile(@Param("studentId") Integer studentId);
}