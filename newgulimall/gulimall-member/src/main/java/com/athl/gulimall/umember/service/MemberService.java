package com.athl.gulimall.umember.service;

import com.athl.gulimall.umember.entity.vo.MemberRegisterVo;
import com.athl.gulimall.umember.entity.vo.UserLoginVo;
import com.athl.gulimall.umember.exception.PhoneExitException;
import com.athl.gulimall.umember.exception.UserNameExitException;
import com.baomidou.mybatisplus.extension.service.IService;
import com.athl.common.utils.PageUtils;
import com.athl.gulimall.umember.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author huanglin
 * @email 2465652971@qq.com
 * @date 2020-07-16 12:24:12
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberRegisterVo memberRegisterVo);

    void checkPhoneUnique(String phone) throws PhoneExitException;
    void checkUserNameUnique(String userName) throws UserNameExitException;

    MemberEntity login(UserLoginVo userLoginVo);
}

