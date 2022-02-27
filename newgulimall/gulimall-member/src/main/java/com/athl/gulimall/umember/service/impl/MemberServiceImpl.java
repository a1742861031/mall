package com.athl.gulimall.umember.service.impl;

import com.athl.gulimall.umember.dao.MemberLevelDao;
import com.athl.gulimall.umember.entity.MemberLevelEntity;
import com.athl.gulimall.umember.entity.vo.MemberRegisterVo;
import com.athl.gulimall.umember.entity.vo.UserLoginVo;
import com.athl.gulimall.umember.exception.PhoneExitException;
import com.athl.gulimall.umember.exception.UserNameExitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.athl.common.utils.PageUtils;
import com.athl.common.utils.Query;
import com.athl.gulimall.umember.dao.MemberDao;
import com.athl.gulimall.umember.entity.MemberEntity;
import com.athl.gulimall.umember.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {
    @Autowired
    private MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberRegisterVo memberRegisterVo) {
        MemberLevelEntity defaultLevel = memberLevelDao.getDefaultLevel();
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setLevelId(defaultLevel.getId());
        /*检查用户名和手机号是否唯一*/
        checkPhoneUnique(memberRegisterVo.getPhone());
        checkUserNameUnique(memberRegisterVo.getUserName());
        memberEntity.setMobile(memberRegisterVo.getPhone());
        memberEntity.setUsername(memberRegisterVo.getUserName());
        //密码加密存储
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(memberRegisterVo.getPassword());
        memberEntity.setPassword(encodePassword);
        baseMapper.insert(memberEntity);
    }

    @Override
    public void checkPhoneUnique(String phone) {
        if (baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone)) > 0) {
            throw new PhoneExitException();
        }
    }

    @Override
    public void checkUserNameUnique(String userName) {
        if (baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName)) > 0) {
            throw new UserNameExitException();
        }
    }

    @Override
    public MemberEntity login(UserLoginVo userLoginVo) {
        QueryWrapper<MemberEntity> wrapper = new QueryWrapper<MemberEntity>().eq("username", userLoginVo.getUserName()).or().eq("mobile", userLoginVo.getUserName());
        MemberEntity member = baseMapper.selectOne(wrapper);
        if (member == null) {
            return null;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean matches = passwordEncoder.matches(userLoginVo.getPassword(), member.getPassword());
            if (matches) {
                return member;
            } else {
                return null;
            }
        }
    }

}