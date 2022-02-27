package com.bobo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description 认证服务
 * @Date 2022/2/17 3:30 PM
 * @Created by bobo
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
public class ApplicationAuth13000 {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationAuth13000.class);
    }
}
