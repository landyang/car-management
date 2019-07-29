package com.example.bean.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 违章实体类
 *
 * @author HaN
 * @create 2019-05-06 20:50
 */
@Data
public class Peccancy {
    //违章ID 自增主键
    private Integer id;
    //车辆
    private Car car;
    //违章时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp date;
    //违章地址
    private String area;
    //违章行为
    private String act;
    //违章代码：不一定有值
    private String code;
    //违章扣分：不一定有值
    private String fen;
    //违章罚款：不一定有值
    private String money;
    //违章状态：0未处理，1已处理
    private String handled;
    //违章城市 不一定有值
    private String wzcity;
}
