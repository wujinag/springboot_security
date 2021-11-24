package com.example.springbootredis2.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonDemo {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redissonClient = Redisson.create(config);
        redissonClient.getBucket("name").set("chen");
        System.out.println(redissonClient.getBucket("name").get());
    }
}
