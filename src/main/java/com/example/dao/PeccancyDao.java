package com.example.dao;

import com.example.bean.entity.Peccancy;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 违章dao层
 *
 * @author HaN
 * @create 2019-05-09 10:00
 */
@Mapper
public interface PeccancyDao {
    /**
     * 批量插入违章记录
     * @param peccancies
     * @return
     */
    Integer insertPeccancies(List<Peccancy> peccancies);

    /**
     * 企业用户查询该企业全部车辆违章：
     * @param comId 企业id
     * @return
     */
    @Select("select p.*\n" +
            "from t_peccancy p INNER JOIN i_car c on p.car_id = c.car_id\n" +
            "where c.com_id=#{comId};")
    @Results({
            @Result(id = true, column = "rs_id", property = "rsId"),
            @Result(column = "car_id", property = "car",
                    one = @One(select = "com.example.dao.CarDao.getCarById", fetchType = FetchType.EAGER))
    })
    List<Peccancy> getAllPeccanciesByComId(Integer comId);

    /**
     * 单位用户查询该单位当前租用的车辆全部违章
     * @param unitId 单位id
     * @return
     */
    @Select("select p.*\n" +
            "from t_peccancy p INNER JOIN (select c.car_id\n" +
            "\tfrom i_car c INNER JOIN r_order_car oc\n" +
            "\ton c.car_id = oc.car_id and oc.order_id in\n" +
            "\t(select order_id\n" +
            "\tfrom r_rental_order\n" +
            "\twhere unit_id = #{unitId} and state_id = 4)) as cars\n" +
            "on p.car_id = cars.car_id")
    @Results({
            @Result(id = true, column = "rs_id", property = "rsId"),
            @Result(column = "car_id", property = "car",
                    one = @One(select = "com.example.dao.CarDao.getCarById", fetchType = FetchType.EAGER))
    })
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
