package com.example.bean.dto;

import com.example.bean.entity.Company;
import com.example.bean.entity.CarModel;
import lombok.Data;

/**
 * 车辆模糊查询分页类
 *
 * @author HaN
 * @create 2019-04-02 20:08
 */
@Data
public class CarPaging {
    //    车牌号
    private String carLicense;
    //    车型
    private CarModel carModel;
    //    归属企业
    private Company company;
    //    归属地
    private String carLocation;
    //    车机型号
    private String ccpType;
    //    车机设备号
    private String ccpCard;
    //    运营状态
    private Integer operationState;

    //第几页
    private Integer pageNum;
    //每页记录数
    private Integer pageSize;
}
