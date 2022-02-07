package com.athl.gulimall.product.web.controller;

import com.athl.gulimall.product.entity.CategoryEntity;
import com.athl.gulimall.product.entity.vo.Catelog2Vo;
import com.athl.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Description 首页请求
 * @Date 2022/2/6 6:07 PM
 * @Created by bobo
 */
@Controller
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/", "/index.html"})
    public String index(Model model) {
        /*查询所有的一级分类*/
        List<CategoryEntity> categoryEntities = categoryService.getLevelOneCategorys();
        model.addAttribute("categorys", categoryEntities);
        /*利用视图解析器进行拼接字符串*/
        return "index";
    }

    @GetMapping("index/catalog.json")
    @ResponseBody
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        return categoryService.getCatalogJson();
    }
}
