package com.cjlu.service;

import com.cjlu.entity.User;

import java.nio.file.AccessDeniedException;

/**
 *
 * 查询用户信息测试
 */
public interface UserService {
    /**
     *
     * @param userId
     * @return
     * @throws AccessDeniedException
     */
    Object getUserDetail(Integer userId) throws AccessDeniedException;
}