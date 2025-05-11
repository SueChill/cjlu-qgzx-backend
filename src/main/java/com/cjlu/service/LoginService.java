package com.cjlu.service;

import com.cjlu.entity.User;

/**
 * 登录服务
 */
public interface LoginService {
    /**
     *
     * @param number
     * @param password
     * @return
     */
    User login(String number, String password);
}