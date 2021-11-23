package com.example.springbootredis1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springbootredis1.dal.mapper")
public class SpringbootRedis1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedis1Application.class, args);
    }

}
