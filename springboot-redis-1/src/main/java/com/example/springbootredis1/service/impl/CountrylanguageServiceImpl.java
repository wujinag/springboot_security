package com.example.springbootredis1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootredis1.dal.mapper.CountrylanguageMapper;
import com.example.springbootredis1.dal.model.Countrylanguage;
import com.example.springbootredis1.service.ICountrylanguageService;
import org.springframework.stereotype.Service;

@Service
public class CountrylanguageServiceImpl extends ServiceImpl<CountrylanguageMapper, Countrylanguage
        > implements ICountrylanguageService {

}
