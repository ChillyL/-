package com.chilly.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auther ChillyLin
 * @date 2020/6/23
 *
 * 配置拦截器
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //定义拦截的位置
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")   //定义需要拦截的路径
                .excludePathPatterns("/admin")  //排除部分必须执行的
                .excludePathPatterns("/admin/login");

    }
}
