package com.chilly.blog.service.impl;

import com.chilly.blog.entity.User;
import com.chilly.blog.mapper.AdminLoginMapper;
import com.chilly.blog.service.AdminLoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.chilly.blog.util.MD5Utils.codeMD5;

/**
 * @auther ChillyLin
 * @date 2020/6/22
 */

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Resource
    private AdminLoginMapper adminLoginMapper;

    @Override
    public User adminLogin(String username, String password) {
        //MD5加密，防止抓包
        return adminLoginMapper.adminLogin(username,codeMD5(password));
    }
}
