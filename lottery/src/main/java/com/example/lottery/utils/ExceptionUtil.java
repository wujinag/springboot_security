
package com.example.lottery.utils;


import com.example.lottery.constants.ReturnCodeEnum;
import com.example.lottery.exception.BizException;
import com.example.lottery.exception.RewardException;
import com.example.lottery.exception.UnRewardException;
import com.example.lottery.service.dto.ResultResp;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ExceptionUtil {

    /**
     * 将下层抛出的异常转换为resp返回码
     *
     * @param e Exception
     * @return
     */
    public static ResultResp handlerException4biz(ResultResp response, Exception e) {
        if (!(e instanceof Exception)) {
            return null;
        } else if (e instanceof BizException) {
            response.setCode(((BizException) e).getErrorCode());
            response.setMsg(e.getMessage());
        } else if (e instanceof RewardException) {
            response.setCode(((RewardException) e).getErrorCode());
            response.setMsg(e.getMessage());
        } else if (e instanceof UnRewardException) {
            response.setCode(((UnRewardException) e).getErrorCode());
            response.setMsg(e.getMessage());
        } else if (e instanceof Exception) {
            log.error("handlerException4biz", e);
            response.setCode(ReturnCodeEnum.SYSTEM_ERROR.getCode());
            response.setMsg(ReturnCodeEnum.SYSTEM_ERROR.getMsg());
        }
        return response;
    }
}
