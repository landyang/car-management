package com.example.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 车辆型号
 *
 * @author HaN
 * @create 2019-03-30 15:28
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class CarModel {
    //车型id
    private Integer modelId;
    //车辆品牌
    @JsonInclude(value = JsonInclude.Include.NON_NULL)//如果该属性为空返回前端不可见
    private CarBrand carBrand;
    //车辆型号
    private String model;
    //车辆图片
    private String modelImage;
    /*//一个车辆型号下有多个车辆
    @JsonInclude(value = JsonInclude.Include.NON_NULL)//如果该属性为空返回前端不可见
    private List<Car> cars;*/
}
