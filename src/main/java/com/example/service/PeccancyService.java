package com.example.service;

import com.example.bean.entity.Peccancy;
import com.example.exception.JuheRequestException;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;

/**
 * 车辆违章service接口
 *
 * @author HaN
 * @create 2019-05-09 14:46
 */
public interface PeccancyService {
    /**
     * 批量查询违章记录，并录入数据库。
     * @param carIds 要查询违章的车辆id集合
     * @return 查询失败的车辆id集合
     */
    Set<Integer> insertPeccancies(List<Integer> carIds) throws Exception;

    /**
     * 聚合全国车辆违章接口剩余请求次数查询
     * @return
     */
    Integer howManyJuheRequstLimit() throws JuheRequestException;

    /**
     * 企业用户查询该企业全部车辆违章
     * @param comId 企业id
     * @return
     */
    List<Peccancy> getAllPeccanciesByComId(Integer comId);

    /**
     * 单位用户查询该单位当前租用的车辆全部违章
     * @param unitId 单位id
     * @return
     */
    List<Peccancy> getAllPeccanciesByUnitId(Integer unitId);

    /**
     * 单位用户修改违章状态(待企业用户审核)
     * hanled 违章状态  0 未处理 1 已处理待审核 2 已处理
     * @param ids
     * @return
     */
    Integer updatePeccanciesHandledByUnit(List<Integer> ids);

    /**
     * 企业用户最终修改违章状态
     * hanled 违章状态  0 未处理 1 已处理待审核 2 已处理
     * @param ids
     * @return
     */
    Integer updatePeccanciesHandledByCompany(List<Integer> ids);
}
