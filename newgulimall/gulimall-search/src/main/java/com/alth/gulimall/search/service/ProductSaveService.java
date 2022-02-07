package com.alth.gulimall.search.service;

import com.athl.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @Description 商品上传到Es
 * @Date 2022/2/6 1:10 PM
 * @Created by bobo
 */
public interface ProductSaveService {
    void save(List<SkuEsModel> skuEsModels) throws IOException;
}
