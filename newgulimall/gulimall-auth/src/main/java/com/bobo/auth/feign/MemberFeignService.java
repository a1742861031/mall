package com.bobo.auth.feign;

import com.athl.common.utils.R;
import com.bobo.auth.vo.UserLoginVo;
import com.bobo.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description 远程调用会员服务
 * @Date 2022/2/19 3:04 PM
 * @Created by bobo
 */
@FeignClient("gulimall-member")
public interface MemberFeignService {
    @PostMapping("member/member/register")
    R register(@RequestBody UserRegisterVo memberRegisterVo);

    @PostMapping("member/member/login")
    R login(@RequestBody UserLoginVo userLoginVo);
}
