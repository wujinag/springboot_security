package com.example.lottery.controller;


import com.example.lottery.constants.RedisKeyManager;
import com.example.lottery.constants.ReturnCodeEnum;
import com.example.lottery.controller.vo.LotteryItemVo;
import com.example.lottery.converter.LotteryConverter;
import com.example.lottery.exception.RewardException;
import com.example.lottery.service.ILotteryService;
import com.example.lottery.service.dto.DoDrawDto;
import com.example.lottery.service.dto.ResultResp;
import com.example.lottery.utils.CusAccessObjectUtil;
import com.example.lottery.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/lottery")
public class LotteryController {
    @Autowired
    ILotteryService lotteryService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    LotteryConverter lotteryConverter;

    /**
     * 抽奖入口
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/{id}")
    public ResultResp<LotteryItemVo> doDraw(@PathVariable("id") Integer id, HttpServletRequest request) {
        String accountIp = CusAccessObjectUtil.getIpAddress(request);
        log.info("begin LotteryController.doDraw,access user {}, lotteryId,{}:", accountIp, id);
        ResultResp<LotteryItemVo> resultResp = new ResultResp<>();
        try {
            //判断当前用户上一次抽奖是否结束
            checkDrawParams(id, accountIp);

            //抽奖
            DoDrawDto dto = new DoDrawDto();
            dto.setAccountIp(accountIp);
            dto.setLotteryId(id);
            lotteryService.doDraw(dto);

            //返回结果设置
            resultResp.setCode(ReturnCodeEnum.SUCCESS.getCode());
            resultResp.setMsg(ReturnCodeEnum.SUCCESS.getMsg());
            //对象转换
            resultResp.setResult(lotteryConverter.dto2LotteryItemVo(dto));
        } catch (Exception e) {
            return ExceptionUtil.handlerException4biz(resultResp, e);
        } finally {
            //清除占位标记
            redisTemplate.delete(RedisKeyManager.getDrawingRedisKey(accountIp));
        }
        return resultResp;
    }

    private void checkDrawParams(Integer id, String accountIp) {
        if (null == id) {
            throw new RewardException(ReturnCodeEnum.REQUEST_PARAM_NOT_VALID.getCode(), ReturnCodeEnum.REQUEST_PARAM_NOT_VALID.getMsg());
        }
        //采用setNx命令，判断当前用户上一次抽奖是否结束
        Boolean result = redisTemplate.opsForValue().setIfAbsent(RedisKeyManager.getDrawingRedisKey(accountIp), "1", 60, TimeUnit.SECONDS);
        //如果为false，说明上一次抽奖还未结束
        if (!result) {
            throw new RewardException(ReturnCodeEnum.LOTTER_DRAWING.getCode(), ReturnCodeEnum.LOTTER_DRAWING.getMsg());
        }
    }
}
