package com.athl.gulimall.product.service;

import com.athl.gulimall.product.entity.vo.Catelog2Vo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.athl.common.utils.PageUtils;
import com.athl.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author huanglin
 * @email 2465652971@qq.com
 * @date 2020-07-16 15:28:09
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenus(List<Long> asList);

    Long[] findCategoryPath(Long catelogId);

    List<CategoryEntity> getLevelOneCategorys();

    Map<String, List<Catelog2Vo>> getCatalogJson();

}

