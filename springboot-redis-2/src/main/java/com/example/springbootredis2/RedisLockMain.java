package com.example.springbootredis2;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class RedisLockMain {

    private static RedissonClient redissonClient;

    static {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redissonClient = Redisson.create(config);
    }

    public static void main(String[] args) throws InterruptedException {
        RLock rLock = redissonClient.getLock("updateRepo");
        for (int i = 0; i < 10; i++) {
            if (rLock.tryLock()) { //返回true，表示获得锁成功
                System.out.println("获得锁成功");
            } else {
                System.out.println("获得锁失败");
            }
            Thread.sleep(2000);
            rLock.unlock();
        }

    }
}
