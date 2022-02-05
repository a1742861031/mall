package com.athl.gulimall.product.service;

import com.athl.common.utils.PageUtils;
import com.athl.gulimall.product.entity.BrandEntity;
import com.athl.gulimall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author huanglin
 * @email 2465652971@qq.com
 * @date 2020-07-16 15:28:09
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryBrandRelationEntity> getBrandCateRelation(Long brandId);

    void saveIdAndName(CategoryBrandRelationEntity categoryBrandRelation);

    List<BrandEntity> getBrandByCatlogId(Long catId);
}

