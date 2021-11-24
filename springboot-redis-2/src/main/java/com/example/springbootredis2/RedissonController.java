package com.example.springbootredis2;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@RestController
public class RedissonController {
    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/")
    public String get() {
        return redissonClient.getBucket("test").get().toString();
    }

}
