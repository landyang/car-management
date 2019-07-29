package com.example.exception;

/**
 * 查询车辆信息失败异常
 * @author HaN
 * @create 2019-05-12 14:33
 */
public class ListCarByIdsException extends RuntimeException {
    public ListCarByIdsException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
