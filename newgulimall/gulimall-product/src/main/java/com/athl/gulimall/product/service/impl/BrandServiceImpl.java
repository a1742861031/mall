package com.athl.gulimall.product.service.impl;

import com.athl.common.utils.PageUtils;
import com.athl.common.utils.Query;
import com.athl.gulimall.product.dao.BrandDao;
import com.athl.gulimall.product.dao.CategoryBrandRelationDao;
import com.athl.gulimall.product.entity.BrandEntity;
import com.athl.gulimall.product.entity.CategoryBrandRelationEntity;
import com.athl.gulimall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Resource
    private CategoryBrandRelationDao categoryBrandRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.eq("brand_id", key).or().like("name", key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void removeBrandAndRelation(List<Long> asList) {
        baseMapper.deleteBatchIds(asList);
        categoryBrandRelationDao.delete(new QueryWrapper<CategoryBrandRelationEntity>()
                .in("brand_id", asList));
    }
}