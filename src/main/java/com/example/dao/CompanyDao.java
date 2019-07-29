package com.example.dao;

import com.example.bean.entity.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 企业公司dao层
 *
 * @author HaN
 * @create 2019-03-31 17:09
 */
@Mapper
public interface CompanyDao {
    @Select("select * from i_company where com_id=#{comId}")
    Company getCompanyById(Integer comId);
}
