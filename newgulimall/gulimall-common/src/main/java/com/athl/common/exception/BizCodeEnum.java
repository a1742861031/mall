package com.athl.common.exception;

/**
 * @author hl
 * @Data 2020/7/20
 */
public enum BizCodeEnum {
    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    SMS_CODE_EXCEPTION(10002, "验证码发送频率过高"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架失败"),
    USER_EXIST_EXCEPTION(15001, "用户已存在"),
    PHONE_EXIST_EXCEPTION(15002, "手机号已存在"),
    USERNAME_OR_PASSWORD_NOTMATCH_EXCEPTION(15003, "用户名或密码错误");
    Integer code;
    String msg;

    BizCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
