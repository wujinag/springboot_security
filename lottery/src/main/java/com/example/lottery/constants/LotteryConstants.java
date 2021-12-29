package com.example.lottery.constants;

public class LotteryConstants {

    /**
     * 表示正在抽奖的用户标记
     */
    public final static String DRAWING = "DRAWING";

    /**
     * 活动标记 LOTTERY:lotteryID
     */
    public final static String LOTTERY = "LOTTERY";

    /**
     * 奖品数据  LOTTERY_PRIZE:lotteryID:PrizeId
     */
    public final static String LOTTERY_PRIZE = "LOTTERY_PRIZE";

    /**
     * 默认奖品数据  DEFAULT_LOTTERY_PRIZE:lotteryID
     */
    public final static String DEFAULT_LOTTERY_PRIZE = "DEFAULT_LOTTERY_PRIZE";

    public enum PrizeTypeEnum {
        THANK(-1), NORMAL(1), UNIQUE(2);
        private int value;

        private PrizeTypeEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    /**
     * 奖项缓存：LOTTERY_ITEM:LOTTERY_ID
     */
    public final static String LOTTERY_ITEM = "LOTTERY_ITEM";

    /**
     * 默认奖项： DEFAULT_LOTTERY_ITEM:LOTTERY_ID
     */
    public final static String DEFAULT_LOTTERY_ITEM = "DEFAULT_LOTTERY_ITEM";

}

