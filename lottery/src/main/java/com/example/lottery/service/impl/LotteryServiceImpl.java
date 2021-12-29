package com.example.lottery.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lottery.constants.LotteryConstants;
import com.example.lottery.constants.RedisKeyManager;
import com.example.lottery.constants.ReturnCodeEnum;
import com.example.lottery.dal.mapper.LotteryItemMapper;
import com.example.lottery.dal.mapper.LotteryMapper;
import com.example.lottery.dal.model.Lottery;
import com.example.lottery.dal.model.LotteryItem;
import com.example.lottery.events.InitPrizeToRedisEvent;
import com.example.lottery.exception.BizException;
import com.example.lottery.exception.UnRewardException;
import com.example.lottery.service.ILotteryService;
import com.example.lottery.service.dto.DoDrawDto;
import com.example.lottery.service.impl.stock.AbstractRewardProcessor;
import com.example.lottery.service.impl.stock.RewardContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Slf4j
@Service
public class LotteryServiceImpl extends ServiceImpl<LotteryMapper, Lottery> implements ILotteryService {

    @Autowired
    LotteryMapper lotteryMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    LotteryItemMapper lotteryItemMapper;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    AsyncLotteryRecordTask asyncLotteryRecordTask;

    private static final int mulriple = 10000;

    @Override
    public void doDraw(DoDrawDto drawDto) throws Exception {
        RewardContext context = new RewardContext();
        LotteryItem lotteryItem = null;
        try {
            //JUC工具 需要等待线程结束之后才能运行
            CountDownLatch countDownLatch = new CountDownLatch(1);
            //判断活动有效性
            Lottery lottery = checkLottery(drawDto);
            //发布事件，用来加载指定活动的奖品信息
            applicationContext.publishEvent(new InitPrizeToRedisEvent(this, lottery.getId(), countDownLatch));
            //开始抽奖
            lotteryItem = doPlay(lottery);
            //记录奖品并扣减库存
            countDownLatch.await(); //等待奖品初始化完成
            String key = RedisKeyManager.getLotteryPrizeRedisKey(lottery.getId(), lotteryItem.getPrizeId());
            int prizeType = Integer.parseInt(redisTemplate.opsForHash().get(key, "prizeType").toString());
            context.setLottery(lottery);
            context.setLotteryItem(lotteryItem);
            context.setAccountIp(drawDto.getAccountIp());
            context.setKey(key);
            //调整库存及记录中奖信息
            AbstractRewardProcessor.rewardProcessorMap.get(prizeType).doReward(context);
        } catch (UnRewardException u) { //表示因为某些问题未中奖，返回一个默认奖项
            context.setKey(RedisKeyManager.getDefaultLotteryPrizeRedisKey(lotteryItem.getLotteryId()));
            lotteryItem = (LotteryItem) redisTemplate.opsForValue().get(RedisKeyManager.getDefaultLotteryItemRedisKey(lotteryItem.getLotteryId()));
            context.setLotteryItem(lotteryItem);
            AbstractRewardProcessor.rewardProcessorMap.get(LotteryConstants.PrizeTypeEnum.THANK.getValue()).doReward(context);
        }
        //拼接返回数据
        drawDto.setLevel(lotteryItem.getLevel());
        drawDto.setPrizeName(context.getPrizeName());
        drawDto.setPrizeId(context.getPrizeId());
    }

    /**
     * 校验当前活动的有效信息
     *
     * @param drawDto
     * @return
     */
    private Lottery checkLottery(DoDrawDto drawDto) {
        Lottery lottery;
        Object lotteryJsonStr = redisTemplate.opsForValue().get(RedisKeyManager.getLotteryRedisKey(drawDto.getLotteryId()));
        if (null != lotteryJsonStr) {
            lottery = JSON.parseObject(lotteryJsonStr.toString(), Lottery.class);
        } else {
            lottery = lotteryMapper.selectById(drawDto.getLotteryId());
        }
        if (lottery == null) {
            throw new BizException(ReturnCodeEnum.LOTTER_NOT_EXIST.getCode(), ReturnCodeEnum.LOTTER_NOT_EXIST.getMsg());
        }
        //判断活动是否结束
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(lottery.getStartTime()) || now.isAfter(lottery.getEndTime())) {
            throw new BizException(ReturnCodeEnum.LOTTER_FINISH.getCode(), ReturnCodeEnum.LOTTER_FINISH.getMsg());
        }
        return lottery;
    }

    //执行抽奖
    private LotteryItem doPlay(Lottery lottery) {
        LotteryItem lotteryItem = null;
        QueryWrapper<LotteryItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lottery_id", lottery.getId());
        Object lotteryItemsObj = redisTemplate.opsForValue().get(RedisKeyManager.getLotteryItemRedisKey(lottery.getId()));
        List<LotteryItem> lotteryItems;
        //说明还未加载到缓存中，同步从数据库加载，并且异步将数据缓存
        if (lotteryItemsObj == null) {
            lotteryItems = lotteryItemMapper.selectList(queryWrapper);
        } else {
            lotteryItems = (List<LotteryItem>) lotteryItemsObj;
        }
        //奖项数据未配置
        if (lotteryItems.isEmpty()) {
            throw new BizException(ReturnCodeEnum.LOTTER_ITEM_NOT_INITIAL.getCode(), ReturnCodeEnum.LOTTER_ITEM_NOT_INITIAL.getMsg());
        }
        int lastScope = 0;
        //将数组随机打乱
        Collections.shuffle(lotteryItems);
        Map<Integer, int[]> awardItemScope = new HashMap<>();
        //item.getPercent=0.05 = 5%
        for (LotteryItem item : lotteryItems) {
            int currentScope = lastScope + new BigDecimal(item.getPercent().floatValue()).multiply(new BigDecimal(mulriple)).intValue();
            awardItemScope.put(item.getId(), new int[]{lastScope + 1, currentScope});
            lastScope = currentScope;
        }
        int luckyNumber = new Random().nextInt(mulriple);
        int luckyPrizeId = 0;
        if (!awardItemScope.isEmpty()) {
            Set<Map.Entry<Integer, int[]>> set = awardItemScope.entrySet();
            for (Map.Entry<Integer, int[]> entry : set) {
                if (luckyNumber >= entry.getValue()[0] && luckyNumber <= entry.getValue()[1]) {
                    luckyPrizeId = entry.getKey();
                    break;
                }
            }
        }
        for (LotteryItem item : lotteryItems) {
            if (item.getId().intValue() == luckyPrizeId) {
                lotteryItem = item;
                break;
            }
        }
        return lotteryItem;
    }
}
