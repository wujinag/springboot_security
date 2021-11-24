package com.example.springbootredis1;


import com.example.springbootredis1.constants.BloomFilterCache;
import com.example.springbootredis1.constants.RedisKeyConstants;
import com.example.springbootredis1.dal.model.City;
import com.example.springbootredis1.service.ICityService;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BloomFilterDataLoadApplicationRunner implements ApplicationRunner {

    @Autowired
    ICityService cityService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<City> cityList = cityService.list();
        // expectedInsertions: 预计添加的元素个数
        // fpp: 误判率
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 10000000, 0.03);
        cityList.parallelStream().forEach(city -> {
            bloomFilter.put(RedisKeyConstants.CITY_KEY + ":" + city.getId());
        });
        BloomFilterCache.cityBloom = bloomFilter;
    }
}