package com.example.springbootredis1.controller;


import com.alibaba.fastjson.JSON;
import com.example.springbootredis1.constants.RedisKeyConstants;
import com.example.springbootredis1.dal.model.City;
import com.example.springbootredis1.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    ICityService cityService;

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/{id}")
    public ResponseEntity<City> city(@PathVariable("id") Integer id) {
        City city = cityService.getById(id);
        return ResponseEntity.ok(city);
    }

    @GetMapping("/redis/{id}")
    public ResponseEntity<City> cityRedis(@PathVariable("id") Integer id) {
        String city = (String) redisTemplate.opsForValue().get(RedisKeyConstants.CITY_KEY + ":" + id);
        return ResponseEntity.ok(JSON.parseObject(city, City.class));
    }
}
