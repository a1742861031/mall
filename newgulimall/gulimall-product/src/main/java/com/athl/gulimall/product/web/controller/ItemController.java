package com.athl.gulimall.product.web.controller;

import com.athl.gulimall.product.entity.vo.SkuInfoVo;
import com.athl.gulimall.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description 详情页
 * @Date 2022/2/16 7:30 PM
 * @Created by bobo
 */
@Controller
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;

    /*查询商品详情*/
    @GetMapping("{skuId}.html")
    public String skuItem(@PathVariable Long skuId, Model mv) {
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfo(skuId);
        mv.addAttribute("item", skuInfoVo);
        return "item";
    }
}
