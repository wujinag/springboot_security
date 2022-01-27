package com.example.lottery.exception;

/**
 * Description： Token自定义异常
 * Author: wu.jiang
 * Date: Created in 2022/1/26 17:51
 * Version: 0.0.1
 */
public class TokenIsExpiredException extends Exception {
    public TokenIsExpiredException() {}

    public TokenIsExpiredException(String message) {
        super(message);
    }

    public TokenIsExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenIsExpiredException(Throwable cause) {
        super(cause);
    }

    public TokenIsExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
