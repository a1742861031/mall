package com.athl.gulimall.umember.entity.vo;

import lombok.Data;

/**
 * @Description 注册验证码Vo
 * @Date 2022/2/18 10:24 PM
 * @Created by bobo
 */
@Data
public class MemberRegisterVo {
    private String userName;
    private String password;
    private String phone;
    private String code;
}
