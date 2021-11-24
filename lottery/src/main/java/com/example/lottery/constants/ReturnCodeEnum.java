package com.example.lottery.constants;

public enum ReturnCodeEnum {

    SUCCESS("0000", "成功"),

    LOTTER_NOT_EXIST("9001", "指定抽奖活动不存在"),

    LOTTER_FINISH("9002", "活动已结束"),

    LOTTER_REPO_NOT_ENOUGHT("9003", "当前奖品库存不足"),

    LOTTER_ITEM_NOT_INITIAL("9004", "奖项数据未初始化"),

    LOTTER_DRAWING("9005", "上一次抽奖还未结束"),

    REQUEST_PARAM_NOT_VALID("9998", "请求参数不正确"),

    SYSTEM_ERROR("9999", "系统繁忙,请稍后重试");

    private String code;

    private String msg;

    private ReturnCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getCodeString() {
        return getCode() + "";
    }
}
