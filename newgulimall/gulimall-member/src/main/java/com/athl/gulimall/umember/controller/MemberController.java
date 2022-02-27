package com.athl.gulimall.umember.controller;

import com.athl.common.exception.BizCodeEnum;
import com.athl.common.utils.PageUtils;
import com.athl.common.utils.R;
import com.athl.gulimall.umember.entity.MemberEntity;
import com.athl.gulimall.umember.entity.vo.MemberRegisterVo;
import com.athl.gulimall.umember.entity.vo.UserLoginVo;
import com.athl.gulimall.umember.exception.PhoneExitException;
import com.athl.gulimall.umember.exception.UserNameExitException;
import com.athl.gulimall.umember.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 会员
 *
 * @author huanglin
 * @email 2465652971@qq.com
 * @date 2020-07-16 12:24:12
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @PostMapping("register")
    public R register(@RequestBody MemberRegisterVo memberRegisterVo) {
        try {
            memberService.register(memberRegisterVo);
        } catch (PhoneExitException e) {
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        } catch (UserNameExitException e) {
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(), BizCodeEnum.USER_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }

    @PostMapping("/login")
    public R login(@RequestBody UserLoginVo userLoginVo) {
        MemberEntity member = memberService.login(userLoginVo);
        if (member != null) {
            return R.ok().put("data", member);
        } else {
            return R.error(BizCodeEnum.USERNAME_OR_PASSWORD_NOTMATCH_EXCEPTION.getCode(), BizCodeEnum.USERNAME_OR_PASSWORD_NOTMATCH_EXCEPTION.getMsg());
        }
    }
}
