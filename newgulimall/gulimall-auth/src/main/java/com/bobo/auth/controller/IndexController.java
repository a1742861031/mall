package com.bobo.auth.controller;

import com.athl.common.utils.HttpUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description 页面跳转
 * @Date 2022/2/17 3:47 PM
 * @Created by bobo
 */
@Controller
public class IndexController {
    @GetMapping("login.html")
    public String loginPage() {
        return "login";
    }


    @GetMapping("reg.html")
    public String regPage() {
        return "reg";
    }
}
