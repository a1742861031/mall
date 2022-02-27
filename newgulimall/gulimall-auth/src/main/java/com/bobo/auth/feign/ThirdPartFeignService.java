package com.bobo.auth.feign;

import com.athl.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description 远程调用第三方服务
 * @Date 2022/2/17 8:30 PM
 * @Created by bobo
 */
@FeignClient("gulimall-third-party")
public interface ThirdPartFeignService {
    @GetMapping("/sms/sendCode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
