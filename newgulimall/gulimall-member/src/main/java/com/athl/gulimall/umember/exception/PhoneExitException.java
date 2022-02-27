package com.athl.gulimall.umember.exception;

/**
 * @Description TODO
 * @Date 2022/2/18 10:55 PM
 * @Created by bobo
 */
public class PhoneExitException extends RuntimeException{
    public PhoneExitException() {
        super("手机号已存在");
    }
}
