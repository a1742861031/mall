package com.athl.gulimall.product.service.impl;

import com.athl.common.constant.ProductConstant;
import com.athl.common.to.SkuHasStokeVo;
import com.athl.common.to.SkuReductionTo;
import com.athl.common.to.SpuBoundsTo;
import com.athl.common.to.es.SkuEsModel;
import com.athl.common.utils.PageUtils;
import com.athl.common.utils.Query;
import com.athl.common.utils.R;
import com.athl.gulimall.product.dao.SpuInfoDao;
import com.athl.gulimall.product.dao.SpuInfoDescDao;
import com.athl.gulimall.product.entity.*;
import com.athl.gulimall.product.entity.vo.*;
import com.athl.gulimall.product.feign.CouponFeignService;
import com.athl.gulimall.product.feign.SearchFeignService;
import com.athl.gulimall.product.feign.WareFeignService;
import com.athl.gulimall.product.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service("spuInfoService")
@Slf4j
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Resource
    private SpuInfoDescDao spuInfoDescDao;

    @Resource
    private SpuImagesService spuImagesService;

    @Resource
    private ProductAttrValueService productAttrValueService;

    @Resource
    private AttrService attrService;

    @Resource
    private SkuInfoService skuInfoService;

    @Resource
    private SkuImagesService skuImagesService;

    @Resource
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WareFeignService wareFeignService;

    @Autowired
    private SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(params), new QueryWrapper<SpuInfoEntity>());

        return new PageUtils(page);
    }

    /**
     * ??????spuInfo??????
     *
     * @param spuSaveVo
     */
    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVo, spuInfoEntity);
        // ??????????????????spu??????
        baseMapper.insert(spuInfoEntity);
        // ????????????spu????????????
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        List<String> decripts = spuSaveVo.getDecript();
        spuInfoDescEntity.setDescript(String.join(",", decripts));
        spuInfoDescDao.insert(spuInfoDescEntity);
        // ????????????spu?????????
        List<String> images = spuSaveVo.getImages();
        if (images != null && images.size() > 0) {
            List<SpuImagesEntity> imagesEntityList = images.stream().map(img -> {
                SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                spuImagesEntity.setSpuId(spuInfoEntity.getId());
                spuImagesEntity.setImgUrl(img);
                return spuImagesEntity;
            }).collect(Collectors.toList());
            spuImagesService.saveBatch(imagesEntityList);
        }
        // ????????????spu???????????????
        List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
        if (baseAttrs != null && baseAttrs.size() > 0) {
            List<ProductAttrValueEntity> productAttrValueEntities = baseAttrs.stream().map(baseAttr -> {
                ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
                productAttrValueEntity.setAttrId(baseAttr.getAttrId());
                productAttrValueEntity.setAttrValue(baseAttr.getAttrValues());
                productAttrValueEntity.setQuickShow(baseAttr.getShowDesc());
                AttrEntity attrEntity = attrService.getById(baseAttr.getAttrId());
                productAttrValueEntity.setAttrName(attrEntity.getAttrName());
                productAttrValueEntity.setSpuId(spuInfoEntity.getId());
                return productAttrValueEntity;
            }).collect(Collectors.toList());
            productAttrValueService.saveBatch(productAttrValueEntities);
        }
        // ????????????spu??????????????? gulimall_sms sms_spu_bounds
        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundsTo spuBoundsTo = new SpuBoundsTo();
        BeanUtils.copyProperties(bounds, spuBoundsTo);
        spuBoundsTo.setSpuId(spuInfoEntity.getId());
        R result1 = couponFeignService.saveSpuBounds(spuBoundsTo);
        if (result1.getCode() != ProductConstant.AttrEnum.SUCCESS_FEIGN.getCode()) {
            log.error("??????spu??????????????????");
        }
        // ????????????spu?????????sku??????
        List<Skus> skus = spuSaveVo.getSkus();
        if (skus != null && skus.size() > 0) {
            // ????????????????????????
            skus.stream().forEach(sku -> {
                String default_img = "";
                for (Images image : sku.getImages()) {
                    if (image.getDefaultImg() == ProductConstant.AttrEnum.DEFAULT_IMG.getCode()) {
                        default_img = image.getImgUrl();
                    }
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(sku, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatelogId(spuInfoEntity.getCatelogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(default_img);
                // 1. sku???????????? pms_sku_info
                skuInfoService.save(skuInfoEntity);
                // skuId
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> imagesEntityList = sku.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    return skuImagesEntity;
                }).filter(item -> {
                    // ???????????????????????????
                    String imgUrl = item.getImgUrl();
                    return (!StringUtils.isEmpty(imgUrl));
                }).collect(Collectors.toList());
                // 2. sku??????????????? pms_sku_images
                skuImagesService.saveBatch(imagesEntityList);
                List<Attr> attr = sku.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntityList = attr.stream().map(a -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(a, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                // 3. sku??????????????? pms_sku_sale_attr_value
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntityList);
                // 4. sku????????????????????? gulimall_sms  sms_sku_full_reduction / sms_sku_ladder / sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(sku, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                    R result2 = couponFeignService.saveSkuReduction(skuReductionTo);
                    if (result2.getCode() != ProductConstant.AttrEnum.SUCCESS_FEIGN.getCode()) {
                        log.error("?????????sku??????????????????????????????");
                    }
                }
            });
        }
    }

    /**
     * ?????????????????????
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.like("spu_name", key).or().like("spu_description", key);
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
        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("publish_status", status);
        }
        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public void up(Long spuId) {
        //???????????????spuid???????????????sku??????
        List<SkuInfoEntity> skus = skuInfoService.getSkuBySpuId(spuId);
        List<Long> skuIds = skus.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
        /*???????????????sku???????????????????????????????????????*/
        List<ProductAttrValueEntity> baseAttrs = productAttrValueService.baseAttrlistforspu(spuId);
        /*???????????????????????????*/
        List<Long> attrIds = baseAttrs.stream().map(attr -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());
        List<Long> searchAttrIds = attrService.selectSearchAttrs(attrIds);
        Set<Long> idSet = new HashSet<>(searchAttrIds);
            List<SkuEsModel.Attrs> attrsList = baseAttrs.stream().filter(item -> {
            return idSet.contains(item.getAttrId());
        }).map(item -> {
            SkuEsModel.Attrs attrs1 = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attrs1);
            return attrs1;
        }).collect(Collectors.toList());
        Map<Long, Boolean> skuStockMap = null;
        try {
            skuStockMap = wareFeignService.getSkuHasStoke(skuIds).stream().collect(Collectors.toMap(SkuHasStokeVo::getSkuId, SkuHasStokeVo::getHasStoke));
        } catch (Exception e) {
            log.error("????????????????????????:{}", e);
        }
        //??????sku??????
        Map<Long, Boolean> finalSkuStockMap = skuStockMap;
        List<SkuEsModel> upProducts = skus.stream().map(sku -> {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, skuEsModel);
            skuEsModel.setSkuPrice(sku.getPrice());
            skuEsModel.setCatalogId(sku.getCatelogId());
            skuEsModel.setSkuImg(sku.getSkuDefaultImg());
            /*?????????????????? ???????????????*/
            if (finalSkuStockMap == null) {
                skuEsModel.setHasStock(true);
            } else {
                skuEsModel.setHasStock(finalSkuStockMap.get(sku.getSkuId()));
            }
            skuEsModel.setHotScore(0L);
            /*????????????sku???????????????*/
            BrandEntity brand = brandService.getById(skuEsModel.getBrandId());
            skuEsModel.setBrandId(brand.getBrandId());
            skuEsModel.setBrandName(brand.getName());
            skuEsModel.setBrandImg(brand.getLogo());
            /*??????sku?????????*/
            CategoryEntity category = categoryService.getById(skuEsModel.getCatalogId());
            skuEsModel.setCatalogName(category.getName());
            skuEsModel.setCatalogId(category.getCatId());
            skuEsModel.setAttrs(attrsList);
            return skuEsModel;
        }).collect(Collectors.toList());
        R r = searchFeignService.productStatusUp(upProducts);
        if (r.getCode() == 0) {
            /*??????spu??????*/
            baseMapper.updateSpuStatus(spuId, ProductConstant.StatusEnum.SPU_UP.getCode());
        } else {
        }
    }
}