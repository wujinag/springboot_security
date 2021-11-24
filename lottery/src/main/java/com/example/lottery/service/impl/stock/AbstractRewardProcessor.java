package com.example.lottery.service.impl.stock;

import com.example.lottery.constants.LotteryConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class AbstractRewardProcessor implements RewardProcessor<RewardContext>, ApplicationContextAware {

    public static Map<Integer, RewardProcessor> rewardProcessorMap = new ConcurrentHashMap<Integer, RewardProcessor>();

    @Autowired
    protected RedisTemplate redisTemplate;

    private void beforeProcessor(RewardContext context) {
    }

    @Override
    public void doReward(RewardContext context) {
        beforeProcessor(context);
        processor(context);
        afterProcessor(context);
    }

    protected abstract void afterProcessor(RewardContext context);


    /**
     * 发放对应的奖品
     *
     * @param context
     */
    protected abstract void processor(RewardContext context);

    /**
     * 返回当前奖品类型
     *
     * @return
     */
    protected abstract int getAwardType();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        rewardProcessorMap.put(LotteryConstants.PrizeTypeEnum.THANK.getValue(), (RewardProcessor) applicationContext.getBean(NoneStockRewardProcessor.class));
        rewardProcessorMap.put(LotteryConstants.PrizeTypeEnum.NORMAL.getValue(), (RewardProcessor) applicationContext.getBean(HasStockRewardProcessor.class));
    }
}
