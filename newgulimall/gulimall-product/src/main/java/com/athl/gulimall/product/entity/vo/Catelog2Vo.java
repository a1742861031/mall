package com.athl.gulimall.product.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description TODO
 * @Date 2022/2/6 6:30 PM
 * @Created by bobo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catelog2Vo {
    private String catalog1Id;//一级父分类id
    private List<Catalog3Vo> catalog3List;//三级自分类
    private String id;
    private String name;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Catalog3Vo {
        private String catalog2Id; //父分类，二级分类id
        private String id;
        private String name;
    }
}
