package com.example.lottery.service.dto;

import lombok.Data;

@Data
public class DoDrawDto {
    //活动id
    private Integer lotteryId;

    private String accountIp;
    //奖品名称
    private String prizeName;
    //中奖登记
    private Integer level;
    //奖品id
    private Integer prizeId;
}
