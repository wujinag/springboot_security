package com.example.lottery.constants;


public class RedisKeyManager {

    /**
     * 正在抽奖的key
     *
     * @param accountIp
     * @return
     */
    public static String getDrawingRedisKey(String accountIp) {
        return new StringBuilder(LotteryConstants.DRAWING).append(":").append(accountIp).toString();
    }

    /**
     * 获取抽奖活动的key
     *
     * @param id
     * @return
     */
    public static String getLotteryRedisKey(Integer id) {
        return new StringBuilder(LotteryConstants.LOTTERY).append(":").append(id).toString();
    }

    /**
     * 获取指定活动下的所有奖品数据
     *
     * @param lotteryId
     * @return
     */
    public static String getLotteryPrizeRedisKey(Integer lotteryId) {
        return new StringBuilder(LotteryConstants.LOTTERY_PRIZE).append(":").append(lotteryId).toString();
    }

    public static String getLotteryPrizeRedisKey(Integer lotteryId, Integer prizeId) {
        return new StringBuilder(LotteryConstants.LOTTERY_PRIZE).append(":").append(lotteryId).append(":").append(prizeId).toString();
    }

    public static String getDefaultLotteryPrizeRedisKey(Integer lotteryId) {
        return new StringBuilder(LotteryConstants.DEFAULT_LOTTERY_PRIZE).append(":").append(lotteryId).toString();
    }

    public static String getLotteryItemRedisKey(Integer lotteryId) {
        return new StringBuilder(LotteryConstants.LOTTERY_ITEM).append(":").append(lotteryId).toString();
    }

    public static String getDefaultLotteryItemRedisKey(Integer lotteryId) {
        return new StringBuilder(LotteryConstants.DEFAULT_LOTTERY_ITEM).append(":").append(lotteryId).toString();
    }
}
