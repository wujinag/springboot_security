package com.example.lottery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lottery.dal.mapper.LotteryRecordMapper;
import com.example.lottery.dal.model.LotteryRecord;
import com.example.lottery.service.ILotteryRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class LotteryRecordServiceImpl extends ServiceImpl<LotteryRecordMapper, LotteryRecord> implements ILotteryRecordService {

}
