package com.athl.gulimall.product.service.impl;

import com.athl.common.utils.PageUtils;
import com.athl.common.utils.Query;
import com.athl.gulimall.product.dao.SkuInfoDao;
import com.athl.gulimall.product.entity.SkuImagesEntity;
import com.athl.gulimall.product.entity.SkuInfoEntity;
import com.athl.gulimall.product.entity.SpuInfoDescEntity;
import com.athl.gulimall.product.entity.vo.SkuInfoVo;
import com.athl.gulimall.product.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getSkuByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(w -> {
                w.like("sku_name", key).or().like("sku_title", key).or().like("sku_subtitle", key);
            });
        }
        String catelogId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            wrapper.eq("catelog_id", catelogId);
        }
        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)) {
            wrapper.eq("brand_id", brandId);
        }
        String min = (String) params.get("min");
        String max = (String) params.get("max");
        if (!StringUtils.isEmpty(max) && !StringUtils.isEmpty(min)) {
            try {
                BigDecimal bigDecimalMin = new BigDecimal(min);
                BigDecimal bigDecimalMax = new BigDecimal(max);
                if (bigDecimalMax.compareTo(bigDecimalMin) == 1) {
                    wrapper.ge("price", min);
                    if (bigDecimalMax.compareTo(new BigDecimal("0")) == 1) {
                        wrapper.lt("price", max);
                    }
                } else {
                    wrapper.ge("price", max);
                    if (bigDecimalMin.compareTo(new BigDecimal("0")) == 1) {
                        wrapper.lt("price", min);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkuBySpuId(Long spuId) {
        return this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
    }

    @Override
    public SkuInfoVo getSkuInfo(Long skuId) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        /*sku基本信息获取*/
        SkuInfoEntity info = baseMapper.selectById(skuId);
        skuInfoVo.setInfo(info);
        /*获取图片信息*/
        List<SkuImagesEntity> imagesEntities = skuImagesService.getImagesBySkuId(skuId);
        skuInfoVo.setImages(imagesEntities);
        /*获取spu的销售属性组合*/
        List<SkuInfoVo.SkuItemSaleAttrVo> saleAttrs = skuSaleAttrValueService.getSaleAttrsBySpuId(info.getSpuId());
        skuInfoVo.setSaleAttrs(saleAttrs);
        /*获取spu的介绍*/
        SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(info.getSpuId());
        skuInfoVo.setDesp(spuInfoDesc);
        /*获取spu的规格参数信息*/
        List<SkuInfoVo.SpuItemBaseAttr> spuItemBaseAttrs = attrGroupService.getAttrGroupWithAttrsBySpuId(info.getSpuId(), info.getCatelogId());
        skuInfoVo.setGroupAttrs(spuItemBaseAttrs);
        System.out.println(skuInfoVo);
        return skuInfoVo;
    }

}