package com.cjlu.service;

import com.cjlu.dto.StudentRegisterDTO;
import com.cjlu.entity.Result;

/**
 *
 */
public interface AuthService {
    /**
     *
     * @param dto
     * @return
     */
    public Result registerStudent(StudentRegisterDTO dto);
}
