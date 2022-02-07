package com.athl.gulimall.product.feign;

import com.athl.common.to.SkuHasStokeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description 远程调用库存服务
 * @Date 2022/2/6 11:43 AM
 * @Created by bobo
 */
@FeignClient("gulimall-ware")
public interface WareFeignService {
    @PostMapping("/ware/waresku/hasStoke")
    List<SkuHasStokeVo> getSkuHasStoke(@RequestBody List<Long> skuIds);
}
