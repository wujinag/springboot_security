package com.example.springbootredis2.client;

public class MainClient {
    public static void main(String[] args) {
        CustomerRedisClient customerRedisClient = new CustomerRedisClient("127.0.0.1", 6379);

        System.out.println(customerRedisClient.set("customer", "jack"));

        System.out.println(customerRedisClient.get("customer"));
    }
}
