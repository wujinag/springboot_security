package com.example.lottery.service.impl.stock;

import com.example.lottery.constants.LotteryConstants;
import com.example.lottery.constants.ReturnCodeEnum;
import com.example.lottery.dal.mapper.LotteryPrizeMapper;
import com.example.lottery.exception.UnRewardException;
import com.example.lottery.service.impl.AsyncLotteryRecordTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class HasStockRewardProcessor extends AbstractRewardProcessor {

    @Autowired
    AsyncLotteryRecordTask asyncLotteryRecordTask;

    @Autowired
    LotteryPrizeMapper lotteryPrizeMapper;

    @Override
    protected void afterProcessor(RewardContext context) {
        asyncLotteryRecordTask.saveLotteryRecord(context.getAccountIp(), context.getLotteryItem(), context.getPrizeName());
    }

    @Override
    protected void processor(RewardContext context) {
        //扣减库存（redis的更新）
        Long result = redisTemplate.opsForHash().increment(context.getKey(), "validStock", -1);
        //当前奖品库存不足，提示未中奖，或者返回一个兜底的奖品
        if (result.intValue() < 0) {
            throw new UnRewardException(ReturnCodeEnum.LOTTER_REPO_NOT_ENOUGHT.getCode(), ReturnCodeEnum.LOTTER_REPO_NOT_ENOUGHT.getMsg());
        }
        List<Object> propertys = Arrays.asList("id", "prizeName");
        List<Object> prizes = redisTemplate.opsForHash().multiGet(context.getKey(), propertys);
        context.setPrizeId(Integer.parseInt(prizes.get(0).toString()));
        context.setPrizeName(prizes.get(1).toString());
        //更新库存（数据库的更新）
        lotteryPrizeMapper.updateValidStock(context.getPrizeId());
    }

    @Override
    protected int getAwardType() {
        return LotteryConstants.PrizeTypeEnum.NORMAL.getValue();
    }
}
