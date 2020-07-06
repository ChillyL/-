package com.chilly.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther ChillyLin
 * @date 2020/6/23
 *
 * 拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    //拦截，在请求之前
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin");  //重定路径
            return false;  //不往下执行
        }else {
            return true;  //继续
        }

    }
}
