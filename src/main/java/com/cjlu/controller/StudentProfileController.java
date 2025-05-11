
package com.cjlu.controller;

import com.cjlu.entity.Student;
import com.cjlu.service.StudentProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生页面信息获取
 */
@RestController
@RequestMapping("/api/student")
public class  StudentProfileController {
    private final StudentProfileService studentProfileService;

    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<Student> getStudentProfile(
            @RequestParam("studentId") Integer studentId) {
        Student profile = studentProfileService.getStudentProfile(studentId);
        return ResponseEntity.ok(profile);
    }
}