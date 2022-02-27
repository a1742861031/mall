package com.bobo.auth.controller;

import com.athl.common.constant.AuthServerConstant;
import com.athl.common.exception.BizCodeEnum;
import com.athl.common.utils.R;
import com.bobo.auth.feign.MemberFeignService;
import com.bobo.auth.feign.ThirdPartFeignService;
import com.bobo.auth.vo.UserLoginVo;
import com.bobo.auth.vo.UserRegisterVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description 登陆
 * @Date 2022/2/17 8:27 PM
 * @Created by bobo
 */
@Controller
@RequestMapping("auth")
public class LoginController {
    @Autowired
    private ThirdPartFeignService thirdPartFeignService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MemberFeignService memberFeignService;

    @ResponseBody
    @GetMapping("/sms/sendCode")
    public R sendCode(@RequestParam("phone") String phone) {
        //先查挡墙redis中是否有该手机号对应的验证码
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CATCH_PREFIX + phone);
        if (StringUtils.isNotEmpty(redisCode)) {
            long time1 = Long.parseLong(redisCode.split("_")[0]);
            long time2 = System.currentTimeMillis();
            //60s内不能再发
            if (time2 - time1 < 60 * 1000) {
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        /*随机生成验证码*/
        String code = "1234" + "_" + System.currentTimeMillis();
        //设置验证码10分钟有效
        //防止60s内再次发现验证码

        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CATCH_PREFIX + phone, code, 10, TimeUnit.MINUTES);
        /*由于要钱，这里就不发送了*/
//        thirdPartFeignService.sendCode(phone, code);
        return R.ok();
    }


    @PostMapping("/register")
    public String register(@Valid UserRegisterVo vo, BindingResult result, RedirectAttributes redirectAttributes) {
        Map<String, String> errors = new HashMap<>();
        //得到所有的错误
        for (FieldError fieldError : result.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
//        Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/reg.html";
        }
        //真正的错误
        //校验验证码
        String inputCode = vo.getCode();
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CATCH_PREFIX + vo.getPhone());
        if (StringUtils.isNotEmpty(redisCode)) {
            if (inputCode.equals(redisCode.split("_")[0])) {
                //验证码校验通过
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CATCH_PREFIX + vo.getPhone());
                //调用远程服务进行注册
                R r = memberFeignService.register(vo);
                if (r.getCode() == 0) {
                    return "redirect:/login.html";
                } else {
                    return "redirect:http://auth.gulimall.com/reg.html";
                }
            } else {
                errors.put("code", "验证码错误");
                redirectAttributes.addFlashAttribute("errors", errors);
                return "redirect:http://auth.gulimall.com/reg.html";
            }

        } else {
            errors.put("code", "验证码错误");
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/reg.html";
        }
    }

    @PostMapping("/login")
    public String login(UserLoginVo loginVo, RedirectAttributes redirectAttributes) {
        //调用远程登陆
        R r = memberFeignService.login(loginVo);
        if (r.getCode() == 0) {
            return "redirect:http://gulimall.com";
        } else {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("msg", r.getMessage());
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }
}
