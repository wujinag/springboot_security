package com.example.lottery.events;

import org.springframework.context.ApplicationEvent;

import java.util.concurrent.CountDownLatch;

/**
 * 事件
 */
public class InitPrizeToRedisEvent extends ApplicationEvent {

    private Integer lotteryId;

    private CountDownLatch countDownLatch;

    public InitPrizeToRedisEvent(Object source, Integer lotteryId, CountDownLatch countDownLatch) {
        super(source);
        this.lotteryId = lotteryId;
        this.countDownLatch = countDownLatch;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

}
