package com.athl.gulimall.product.feign;

import com.athl.common.to.es.SkuEsModel;
import com.athl.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description ES远程接口
 * @Date 2022/2/6 1:26 PM
 * @Created by bobo
 */
@FeignClient("gulimall-search")
public interface SearchFeignService {
    @PostMapping("/search/save/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
