package com.chilly.blog.service;

import com.chilly.blog.entity.User;

public interface AdminLoginService {
    /**
     * 获取用户
     * @param username
     * @param password
     * @return
     */
    User adminLogin(String username,String password);
}
