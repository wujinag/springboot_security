package com.example.lottery.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/lottery-item")
public class LotteryItemController {

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping
    public void test() {
        ValueOperations vo = redisTemplate.opsForValue();
        vo.get("d");
    }
}
