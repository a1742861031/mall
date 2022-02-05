package com.athl.gulimall.coupon.service;

import com.athl.common.to.SpuBoundsTo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.athl.common.utils.PageUtils;
import com.athl.gulimall.coupon.entity.SpuBoundsEntity;

import java.util.Map;

/**
 * 商品spu积分设置
 *
 * @author huanglin
 * @email 2465652971@qq.com
 * @date 2020-07-16 15:15:16
 */
public interface SpuBoundsService extends IService<SpuBoundsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuBounds(SpuBoundsTo spuBoundsTo);
}

