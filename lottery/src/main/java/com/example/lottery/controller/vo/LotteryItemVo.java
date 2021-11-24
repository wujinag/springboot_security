package com.example.lottery.controller.vo;

import lombok.Data;

@Data
public class LotteryItemVo {
    private Integer lotteryId;
    //中奖用户ip
    private String accountIp;
    //奖品名称
    private String prizeName;
    //中奖登记
    private Integer level;
    //奖品id
    private Integer prizeId;
}
