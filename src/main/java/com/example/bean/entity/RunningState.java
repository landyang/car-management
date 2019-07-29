package com.example.bean.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *运行状态
 *
 * @author HaN
 * @create 2019-04-25 12:58
 */
@Data
public class RunningState {
    //运行状态Id
    private Integer rsId;
    //对应车辆
    private Car car;
    //运行状态 0离线、1行驶、2静止、3未激活
    private Integer rsType;
    //时间
    //出参格式化
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp rsTime;
    //轨迹经度
    private BigDecimal rsLongitude;
    //轨迹纬度
    private BigDecimal rsLatitude;
    //车速
    private Integer rsSpeed;
}
