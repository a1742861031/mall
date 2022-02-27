package com.bobo.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description 添加路径映射
 * @Date 2022/2/18 9:57 PM
 * @Created by bobo
 */
@Configuration
public class GuliMallWebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("login.html").setViewName("login");
        registry.addViewController("reg.html").setViewName("reg");
    }
}
