package com.example.lottery;

import com.example.lottery.dal.mapper.LotteryMapper;
import com.example.lottery.dal.model.Lottery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class LoadDataApplicationRunner implements ApplicationRunner {


    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    LotteryMapper lotteryMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("=========begin load lottery data to Redis===========");
        //加载当前抽奖活动信息
        Lottery lottery = lotteryMapper.selectById(1);

        log.info("=========finish load lottery data to Redis===========");
    }
}
