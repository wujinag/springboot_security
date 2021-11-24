package com.example.lottery.exception;

/**
 * 业务层异常类
 */
public class RewardException extends BaseBusinessException {

    public RewardException() {
        super();
    }

    public RewardException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public RewardException(Throwable arg0) {
        super(arg0);
    }

    public RewardException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public RewardException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public RewardException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }
}
