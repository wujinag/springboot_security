package com.example.springbootredis1;

import com.alibaba.fastjson.JSON;
import com.example.springbootredis1.constants.RedisKeyConstants;
import com.example.springbootredis1.dal.model.City;
import com.example.springbootredis1.service.ICityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 项目启动加载数据至缓存中
 */
@Slf4j
@Component
public class LoadDataApplicationRunner implements ApplicationRunner {

    @Autowired
    ICityService cityService;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("=========begin load city data to Redis===========");
        List<City> cityList = cityService.list();
        cityList.parallelStream().forEach(city -> {
            redisTemplate.opsForValue().set(RedisKeyConstants.CITY_KEY + ":" + city.getId(), JSON.toJSONString(city));
        });
        log.info("=========finish load city data to Redis===========");
    }
}
