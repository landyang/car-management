package com.example.bean.entity;

import lombok.Data;

/**
 * 用户实体类
 *
 * @author HaN
 * @create 2019-04-29 10:39
 */
@Data
public class User {
    //用户id
    private Integer userId;
    //用户所属单位
    private Integer userUnit;

    //用户类型
    private Integer userType;
}
