package com.example.enums;

/**
 * @author HaN
 * @create 2019-04-29 10:22
 */
public enum UserTypeEnum {
    PERSONAL_USER(1,"个人用户"),
    UNIT_USER(2,"单位用户"),
    COMPANY_USER(3,"企业用户");

    private int code;
    private String type;

    UserTypeEnum (int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
