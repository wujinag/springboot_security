package com.example.lottery.controller;


import com.example.lottery.constants.ReturnCodeEnum;
import com.example.lottery.dal.model.LotteryRecord;
import com.example.lottery.service.ILotteryRecordService;
import com.example.lottery.service.dto.ResultResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/lottery-record")
public class LotteryRecordController {

    @Autowired
    ILotteryRecordService lotteryRecordService;

    @GetMapping
    public ResultResp<List<LotteryRecord>> records() {
        List<LotteryRecord> records = lotteryRecordService.list();
        ResultResp<List<LotteryRecord>> resultResp = new ResultResp<>();
        resultResp.setMsg(ReturnCodeEnum.SUCCESS.getMsg());
        resultResp.setCode(ReturnCodeEnum.SUCCESS.getCode());
        resultResp.setResult(records);
        return resultResp;
    }
}
