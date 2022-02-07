package com.alth.gulimall.search.controller;

import com.alth.gulimall.search.service.ProductSaveService;
import com.athl.common.exception.BizCodeEnum;
import com.athl.common.to.es.SkuEsModel;
import com.athl.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @Description 保存到ES
 * @Date 2022/2/6 1:07 PM
 * @Created by bobo
 */
@RestController
@Slf4j
@RequestMapping("search/save")
public class ElasticSearchSaveController {
    @Autowired
    private ProductSaveService productSaveService;

    /*上架商品*/
    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {
        try {
            productSaveService.save(skuEsModels);
        } catch (IOException e) {
            log.error("商品上架失败:{}", e);
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }
        return R.ok();
    }
}
