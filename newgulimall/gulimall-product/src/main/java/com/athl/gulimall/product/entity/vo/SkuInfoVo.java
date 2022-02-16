package com.athl.gulimall.product.entity.vo;

import com.athl.gulimall.product.entity.SkuImagesEntity;
import com.athl.gulimall.product.entity.SkuInfoEntity;
import com.athl.gulimall.product.entity.SpuInfoDescEntity;
import com.athl.gulimall.product.entity.SpuInfoEntity;
import lombok.Data;

import java.util.List;

/**
 * @Description sku详情Vo
 * @Date 2022/2/16 8:16 PM
 * @Created by bobo
 */
@Data
public class SkuInfoVo {
    /*sku基本信息获取*/
    SkuInfoEntity info;
    /*sku的图片信息*/
    List<SkuImagesEntity> images;
    /*获取spu的销售属性组合*/
    List<SkuItemSaleAttrVo> saleAttrs;
    /*获取spu的介绍*/
    SpuInfoDescEntity desp;
    /*获取spu的规格参数信息*/
    List<SpuItemBaseAttr> groupAttrs;

    @Data
    public static class SkuItemSaleAttrVo {
        private Long attrId;
        private String attrName;
        private String   attrValues;
    }

    @Data
    public static class SpuItemBaseAttr {
        private String groupName;
        private List<SpuBaseAttrVo> attrs;
    }

    @Data
    public static class SpuBaseAttrVo {
        private String attrName;
        private String attrValue;
    }
}
