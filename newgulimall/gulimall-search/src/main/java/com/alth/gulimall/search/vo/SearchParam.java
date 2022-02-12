package com.alth.gulimall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description 封装所有可能的检索条件
 * @Date 2022/2/8 12:41 PM
 * @Created by bobo
 */
@Data
public class SearchParam {
    private String keyword;//关键字检索
    private Long catalog3Id;//三级分类id
    private String sort;//排序规则
    private Integer hasStock = 1;//是否只显示有货 (1 表示查询有库存 0表示查询所有)
    private String skuPrice;//价格区间
    private List<Long> brandId;//品牌id 可以多选
    private List<String> attrs;//商品属性
    private Integer pageNum = 1;//页码
}
