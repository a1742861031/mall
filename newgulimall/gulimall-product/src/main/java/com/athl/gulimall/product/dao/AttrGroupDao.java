package com.athl.gulimall.product.dao;

import com.athl.gulimall.product.entity.AttrGroupEntity;
import com.athl.gulimall.product.entity.vo.SkuInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 * 
 * @author huanglin
 * @email 2465652971@qq.com
 * @date 2020-07-16 15:28:09
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {


    List<SkuInfoVo.SpuItemBaseAttr> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("cateLogId") Long cateLogId);
}
