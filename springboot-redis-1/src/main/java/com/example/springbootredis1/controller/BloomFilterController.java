package com.example.springbootredis1.controller;

import com.example.springbootredis1.constants.BloomFilterCache;
import com.example.springbootredis1.constants.RedisKeyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BloomFilterController {
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/bloom/{id}")
    public String filter(@PathVariable("id") Integer id) {
        String key = RedisKeyConstants.CITY_KEY + ":" + id;
        if (BloomFilterCache.cityBloom.mightContain(key)) { //判断当前数据在布隆过滤器中是否存在，如果存在则从缓存中加载
            return redisTemplate.opsForValue().get(key).toString();
        }
        return "数据不存在";
    }
}