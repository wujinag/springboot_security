package com.example.lottery.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.lottery.dal.model.LotteryPrize;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.io.Serializable;

/**
 * <p>
 * Mapper 接口
 * </p>
 */
public interface LotteryPrizeMapper extends BaseMapper<LotteryPrize> {

    @Update("update lottery_prize set valid_stock=valid_stock-1 where valid_stock>=1 and id=#{id}")
    void updateValidStock(@Param("id") Serializable id);
}
