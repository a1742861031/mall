package com.athl.common.constant;

import lombok.Getter;

/**
 * @author hl
 * @Data 2020/7/26
 */
public class ProductConstant {
    @Getter
    public enum AttrEnum {

        ATTR_TYPE_BASE(1, "基本属性"), ATTR_TYPE_SALE(0, "销售属性"),
        DEFAULT_IMG(1, "默认图片"), NOT_DEFAULT_IMG(0, "非默认图片"),
        SUCCESS_FEIGN(0, "远程服务调用成功"), ERROR_FEIGN(1, "远程服务调用失败");
        private int code;
        private String msg;

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
