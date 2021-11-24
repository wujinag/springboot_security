package com.example.lottery.service.impl.stock;

import com.example.lottery.constants.LotteryConstants;
import com.example.lottery.service.impl.AsyncLotteryRecordTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NoneStockRewardProcessor extends AbstractRewardProcessor {

    @Autowired
    AsyncLotteryRecordTask asyncLotteryRecordTask;

    @Override
    protected void afterProcessor(RewardContext context) {
        asyncLotteryRecordTask.saveLotteryRecord(context.getAccountIp(), context.getLotteryItem(), context.getPrizeName());
    }

    @Override
    protected void processor(RewardContext context) {
        List<Object> propertys = Arrays.asList("id", "prizeName");
        List<Object> prizes = redisTemplate.opsForHash().multiGet(context.getKey(), propertys);
        context.setPrizeId(Integer.parseInt(prizes.get(0).toString()));
        context.setPrizeName(prizes.get(1).toString());
    }

    @Override
    protected int getAwardType() {
        return LotteryConstants.PrizeTypeEnum.THANK.getValue();
    }
}
