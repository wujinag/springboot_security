package com.example.springbootredis2.redisson;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedissonController {

    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/redisson/get")
    public String get() {
        return redissonClient.getBucket("name").get().toString();
    }
}
