package com.alth.gulimall.search.vo;

import com.athl.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.List;

/**
 * @Description 检索结果
 * @Date 2022/2/8 12:57 PM
 * @Created by bobo
 */
@Data
public class SearchResult {
    private List<SkuEsModel> products;//商品
    private Integer pageNum; //返回的当前页码
    private Long total;//总记录数
    private Integer totalPages;//总页数
    private List<BrandVo> brands;//查询结果涉及到的所有品牌
    private List<AttrVo> attrs; //查询涉及到的所有属性
    private List<CatalogVo> catalogs; //涉及到的所有分类

    @Data
    public static class BrandVo {
        private Long brandId;
        private String brandName;
        private String brandImg;
    }

    @Data
    public static class AttrVo {
        private Long attrId;
        private String attrName;
        private List<String> attrValues;
    }

    @Data
    public static class CatalogVo{
        private Long catalogId;
        private String catalogName;

    }
}
