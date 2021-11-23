package com.example.springbootredis1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootredis1.dal.mapper.CountryMapper;
import com.example.springbootredis1.dal.model.Country;
import com.example.springbootredis1.service.ICountryService;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country> implements ICountryService {

}
