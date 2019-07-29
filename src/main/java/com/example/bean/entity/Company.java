package com.example.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * 企业
 *
 * @author HaN
 * @create 2019-03-31 10:18
 */
@Data
public class Company {
    private Integer comId;
    private String comName;
    /*以下字段查询车辆时序列化和反序列化忽略*/
    @JsonIgnore
    private String comLicense;
    @JsonIgnore
    private String comRepresent;
    @JsonIgnore
    private Date comStarttime;
    @JsonIgnore
    private Date comLimittime;
    @JsonIgnore
    private String comLocate;
    @JsonIgnore
    private String comPhone;
    @JsonIgnore
    private String comInfo;
}
