package com.example.enums;

/**
 * 车辆运营状态
 * @author HaN
 * @create 2019-05-25 20:15
 */
public enum CarOperationStateEnum {
    OUTAGE(0,"停运"),
    NORMAL(1,"正常");

    private int code;
    private String state;

    CarOperationStateEnum (int code, String state) {
        this.code = code;
        this.state = state;
    }

    public int getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
}
