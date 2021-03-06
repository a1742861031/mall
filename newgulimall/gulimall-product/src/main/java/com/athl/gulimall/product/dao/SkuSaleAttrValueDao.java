package com.athl.gulimall.product.dao;

import com.athl.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.athl.gulimall.product.entity.vo.SkuInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * sku销售属性&值
 * 
 * @author huanglin
 * @email 2465652971@qq.com
 * @date 2020-07-16 15:28:09
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<SkuInfoVo.SkuItemSaleAttrVo> getSaleAttrsBySpuId(Long spuId);
}
