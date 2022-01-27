package com.example.lottery.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lottery.dal.model.JwtUser;
import com.example.lottery.dal.model.LotteryRole;
import com.example.lottery.dal.model.LotteryRoleUser;
import com.example.lottery.dal.model.LotteryUser;
import com.example.lottery.service.ILotteryRoleService;
import com.example.lottery.service.ILotteryRoleUserService;
import com.example.lottery.service.ILotteryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description： Spring-Security自定义身份认证类(实现UserDetailsService并重写loadUserByUsername方法)
 *  * 在loadUserByUsername方法内校验用户名密码是否正确并返回一个UserDetails对象
 * Author: wu.jiang
 * Date: Created in 2022/1/26 17:54
 * Version: 0.0.1
 *
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ILotteryUserService userService;
    @Autowired
    private ILotteryRoleService roleService;
    @Autowired
    private ILotteryRoleUserService roleUserService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        LambdaQueryWrapper<LotteryUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LotteryUser::getAccount,account);
        LotteryUser one = userService.getOne(queryWrapper);
        LambdaQueryWrapper<LotteryRoleUser> queryWrapper1 = new LambdaQueryWrapper<LotteryRoleUser>();
        queryWrapper1.eq(LotteryRoleUser::getUserId,one.getId());
        List<LotteryRoleUser> list = roleUserService.list(queryWrapper1);
        Set<String> roles = new HashSet<>();
        List<Integer> collect = list.stream().map(LotteryRoleUser::getRoleId).collect(Collectors.toList());
        //遍历获取role
        collect.forEach(e->{
            LotteryRole byId = roleService.getById(e);
            roles.add(byId.getRole());
        });
        one.setRoles(roles);
        return new JwtUser(one);
    }
}
