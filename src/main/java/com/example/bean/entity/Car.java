package com.example.bean.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 车辆信息
 *
 * @author HaN
 * @create 2019-03-28 21:55
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class Car {
    //    车辆id
    private Integer carId;
    //    车牌号
    private String carLicense;
    //    车辆VIN码(由十七个英数组成,英文字母“I”、“O”、“Q”均不会被使用)
    private String carVin;
    //    车型
    private CarModel carModel;
    //    发动机号
    private String engineCode;
    //    归属企业
    private Company company;
    //    归属地
    private String carLocation;
    //    注册时间
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date carJointime;
    //    上次保养日期
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date maintainTime;
    //    车险信息
    private String carInsurance;
    //    参考租金
    private Integer carPrice;
    //    车辆颜色
    private String carColor;
    //    座位数量
    private Integer carSeats;
    //    车机型号
    private String ccpType;
    //    车机设备号
    private String ccpCard;
    //    运营状态：0停运，1在运营
    private Integer operationState;
}
