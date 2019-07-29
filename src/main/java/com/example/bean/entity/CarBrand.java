package com.example.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 车辆品牌
 *
 * @author HaN
 * @create 2019-03-29 19:52
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class CarBrand {
    //    车型id
    private Integer brandId;
    //    汽车公司全称
    private String fullname;
    //    汽车公司简称
    private String simplename;
    //    公司图标
    private String brandIcon;
    //    公司简介
    private String synopsis;
    //一个汽车公司下有多个车辆型号
    @JsonInclude(value = JsonInclude.Include.NON_NULL)//如果该属性为空返回前端不可见
    private List<CarModel> carModels;
}
