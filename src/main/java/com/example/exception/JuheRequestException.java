package com.example.exception;

/**
 * 聚合数据接口请求异常
 * @author HaN
 * @create 2019-05-18 12:18
 */
public class JuheRequestException extends RuntimeException {
    public JuheRequestException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
