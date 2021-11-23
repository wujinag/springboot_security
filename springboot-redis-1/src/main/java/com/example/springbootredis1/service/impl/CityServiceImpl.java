package com.example.springbootredis1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootredis1.dal.mapper.CityMapper;
import com.example.springbootredis1.dal.model.City;
import com.example.springbootredis1.service.ICityService;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

}
