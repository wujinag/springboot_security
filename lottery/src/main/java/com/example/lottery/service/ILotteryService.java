package com.example.lottery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.lottery.dal.model.Lottery;
import com.example.lottery.service.dto.DoDrawDto;

/**
 * <p>
 * 服务类
 * </p>
 */
public interface ILotteryService extends IService<Lottery> {

    void doDraw(DoDrawDto drawDto) throws Exception;
}
