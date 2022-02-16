package com.athl.gulimall.product.service.impl;

import com.athl.gulimall.product.entity.vo.SkuInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.athl.common.utils.PageUtils;
import com.athl.common.utils.Query;

import com.athl.gulimall.product.dao.SkuSaleAttrValueDao;
import com.athl.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.athl.gulimall.product.service.SkuSaleAttrValueService;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoVo.SkuItemSaleAttrVo> getSaleAttrsBySpuId(Long spuId) {
        SkuSaleAttrValueDao skuSaleAttrValueDao = this.getBaseMapper();
        return skuSaleAttrValueDao.getSaleAttrsBySpuId(spuId);
    }

}