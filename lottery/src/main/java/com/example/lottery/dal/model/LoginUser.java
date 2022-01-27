package com.example.lottery.dal.model;

import lombok.Data;

/**
 * Description：登录对象
 * Author: wu.jiang
 * Date: Created in 2022/1/26 16:54
 * Version: 0.0.1
 */
@Data
public class LoginUser extends LotteryUser{

    /**
     * 是否记住我
     */
    private Integer rememberMe;
}
