package com.example.lottery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lottery.dal.model.LoginUser;
import com.example.lottery.dal.model.LotteryUser;
import com.example.lottery.service.ILotteryUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class IndexController {

    private ILotteryUserService userService;
    /**
     * 主界面
     *
     * @return
     */
    //@GetMapping("/")
    //public String index(ModelMap map) {
    //    return "index";
    //}

    /**
     * 登录页
     *
     * @return
     */
    @GetMapping("/index")
    public String login(ModelMap map) {
        ////单个数据
        //map.put("account", "账号");
        //LoginUser user = new LoginUser();
        //user.setPassword("test_ps");
        //user.setAccount("test");
        //map.put("loginUser", user);
        return "login";
    }

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("/login")
    public String login(@ModelAttribute LoginUser loginUser) {
        // 检验用户名或密码,跳转成功
        LambdaQueryWrapper<LotteryUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(LotteryUser::getAccount,loginUser.getAccount());
        userQueryWrapper.eq(LotteryUser::getPassword,loginUser.getPassword());
        LotteryUser one = userService.getOne(userQueryWrapper);
        if(Objects.nonNull(one)){
            //主界面
            return "index";
        }
            return "error";
    }

}
