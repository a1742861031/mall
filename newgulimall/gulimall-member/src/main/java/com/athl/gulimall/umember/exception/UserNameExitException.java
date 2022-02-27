package com.athl.gulimall.umember.exception;

/**
 * @Description 用户名存在异常
 * @Date 2022/2/18 10:56 PM
 * @Created by bobo
 */
public class UserNameExitException extends RuntimeException{
    public UserNameExitException() {
        super("用户名已存在");
    }
}
