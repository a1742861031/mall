package com.athl.gulimall.ware.entity.vo;

import lombok.Data;

/**
 * @Description 查询是否有库存
 * @Date 2022/2/6 11:34 AM
 * @Created by bobo
 */
@Data
public class SkuHasStokeVo {
    private Long skuId;
    private Boolean hasStoke;
}
