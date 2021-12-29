package com.example.lottery.events;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lottery.constants.RedisKeyManager;
import com.example.lottery.dal.mapper.LotteryItemMapper;
import com.example.lottery.dal.mapper.LotteryPrizeMapper;
import com.example.lottery.dal.model.LotteryItem;
import com.example.lottery.dal.model.LotteryPrize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 事件监听
 */
@Slf4j
@Component
public class InitPrizeToRedisListener implements ApplicationListener<InitPrizeToRedisEvent> {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    LotteryPrizeMapper lotteryPrizeMapper;

    @Autowired
    LotteryItemMapper lotteryItemMapper;

    @Override
    public void onApplicationEvent(InitPrizeToRedisEvent initPrizeToRedisEvent) {
        log.info("begin InitPrizeToRedisListener," + initPrizeToRedisEvent);
        Boolean result = redisTemplate.opsForValue().setIfAbsent(RedisKeyManager.getLotteryPrizeRedisKey(initPrizeToRedisEvent.getLotteryId()), "1");
        //已经初始化到缓存中了，不需要再次缓存
        if (!result) {
            log.info("already initial");
            initPrizeToRedisEvent.getCountDownLatch().countDown();
            return;
        }
        QueryWrapper<LotteryItem> lotteryItemQueryWrapper = new QueryWrapper<>();
        lotteryItemQueryWrapper.eq("lottery_id", initPrizeToRedisEvent.getLotteryId());
        List<LotteryItem> lotteryItems = lotteryItemMapper.selectList(lotteryItemQueryWrapper);

        //如果指定的奖品没有了，会生成一个默认的奖项
        LotteryItem defaultLotteryItem = lotteryItems.parallelStream().filter(o -> o.getDefaultItem().intValue() == 1).findFirst().orElse(null);

        Map<String, Object> lotteryItemMap = new HashMap<>(16);
        lotteryItemMap.put(RedisKeyManager.getLotteryItemRedisKey(initPrizeToRedisEvent.getLotteryId()), lotteryItems);
        lotteryItemMap.put(RedisKeyManager.getDefaultLotteryItemRedisKey(initPrizeToRedisEvent.getLotteryId()), defaultLotteryItem);
        redisTemplate.opsForValue().multiSet(lotteryItemMap);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("lottery_id", initPrizeToRedisEvent.getLotteryId());
        List<LotteryPrize> lotteryPrizes = lotteryPrizeMapper.selectList(queryWrapper);

        //保存一个默认奖项
        AtomicReference<LotteryPrize> defaultPrize = new AtomicReference<>();
        lotteryPrizes.stream().forEach(lotteryPrize -> {
            if (lotteryPrize.getId().equals(defaultLotteryItem.getPrizeId())) {
                defaultPrize.set(lotteryPrize);
            }
            String key = RedisKeyManager.getLotteryPrizeRedisKey(initPrizeToRedisEvent.getLotteryId(), lotteryPrize.getId());
            setLotteryPrizeToRedis(key, lotteryPrize);
        });
        String key = RedisKeyManager.getDefaultLotteryPrizeRedisKey(initPrizeToRedisEvent.getLotteryId());
        setLotteryPrizeToRedis(key, defaultPrize.get());
        initPrizeToRedisEvent.getCountDownLatch().countDown(); //表示初始化完成
        log.info("finish InitPrizeToRedisListener," + initPrizeToRedisEvent);
    }

    private void setLotteryPrizeToRedis(String key, LotteryPrize lotteryPrize) {
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        redisTemplate.opsForHash().put(key, "id", lotteryPrize.getId());
        redisTemplate.opsForHash().put(key, "lotteryId", lotteryPrize.getLotteryId());
        redisTemplate.opsForHash().put(key, "prizeName", lotteryPrize.getPrizeName());
        redisTemplate.opsForHash().put(key, "prizeType", lotteryPrize.getPrizeType());
        redisTemplate.opsForHash().put(key, "totalStock", lotteryPrize.getTotalStock());
        redisTemplate.opsForHash().put(key, "validStock", lotteryPrize.getValidStock());
    }
}

