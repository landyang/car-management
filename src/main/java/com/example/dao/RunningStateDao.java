package com.example.dao;

import com.example.bean.entity.RunningState;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.sql.Timestamp;
import java.util.List;

/**
 * 运行状态dao层
 *
 * @author HaN
 * @create 2019-04-27 11:11
 */
@Mapper
public interface RunningStateDao {
    /**
     * 查询指定车辆当前运行信息
     * @param carId
     * @return
     */
    @Select("SELECT * FROM t_running_state " +
            "where car_id = #{carId} " +
            "ORDER BY rs_time desc " +
            "LIMIT 1")
    @Results({
            @Result(id = true, column = "rs_id", property = "rsId"),
            @Result(column = "car_id", property = "car",
                    one = @One(select = "com.example.dao.CarDao.getCarById", fetchType = FetchType.EAGER))
    })
    RunningState getCurrentRunnningStateByCarId(Integer carId);

    /**
     * 查询指定车辆某一时间段的所有运行状态
     * @param carId
     * @param beginTime
     * @param endTime
     * @return
     */
    @Select("select *\n" +
            "from t_running_state\n" +
            "where car_id = #{carId} and rs_time BETWEEN #{beginTime} and #{endTime}")
    List<RunningState> getRunningStatesByCarIdAndTime(Integer carId, Timestamp beginTime,Timestamp endTime);

    /**
     * 查询某单位所租车辆的当前运行状态
     * @param unitId
     * @return
     */
    //该查询语句bug运行状态表中无所租车记录则不会显示
//    @Select("SELECT *\n" +
//            "from t_running_state as rs2 INNER JOIN\n" +
//            "\t\t(SELECT rs1.car_id,max(rs1.rs_id) as max_rs_id\n" +
//            "\t\tfrom t_running_state as rs1 INNER JOIN \n" +
//            "\t\t\t\t(select DISTINCT c.car_id\n" +
//            "\t\t\t\tfrom i_car c INNER JOIN r_order_car oc\n" +
//            "\t\t\t\ton c.car_id = oc.car_id and oc.order_id in\n" +
//            "\t\t\t\t(select order_id\n" +
//            "\t\t\t\tfrom r_rental_order\n" +
//            "\t\t\t\twhere unit_id = #{unitId} and state_id = 4)) as cars\n" +
//            "\t\ton rs1.car_id = cars.car_id\n" +
//            "\t\tGROUP BY rs1.car_id) as medium\n" +
//            "on rs2.rs_id = medium.max_rs_id")
//    @Results({
//            @Result(id = true, column = "rs_id", property = "rsId"),
//            @Result(column = "car_id", property = "car",
//                    one = @One(select = "com.example.dao.CarDao.getCarById", fetchType = FetchType.EAGER))
//    })
//    List<RunningState> getAllCurrentRunnningStateByUnitId(Integer unitId);
    @Select("select rs2.rs_id,\n" +
            "medium.car_id,\n" +
            "rs2.rs_type,\n" +
            "rs2.rs_time,\n" +
            "rs2.rs_longitude,\n" +
            "rs2.rs_latitude,\n" +
            "rs2.rs_speed\n" +
            "from t_running_state as rs2 RIGHT JOIN\n" +
            "\t\t(SELECT cars.car_id,max(rs1.rs_id) as max_rs_id\n" +
            "\t\tfrom t_running_state as rs1 RIGHT JOIN \n" +
            "\t\t\t\t(select DISTINCT c.car_id\n" +
            "\t\t\t\tfrom i_car c INNER JOIN r_order_car oc\n" +
            "\t\t\t\ton c.car_id = oc.car_id and oc.order_id in\n" +
            "\t\t\t\t(select order_id\n" +
            "\t\t\t\tfrom r_rental_order\n" +
            "\t\t\t\twhere unit_id = #{unitId} and state_id = 4)) as cars\n" +
            "\t\ton rs1.car_id = cars.car_id\n" +
            "\t\tGROUP BY cars.car_id) as medium\n" +
            "on rs2.rs_id = medium.max_rs_id")
    @Results({
            @Result(id = true, column = "rs_id", property = "rsId"),
            @Result(column = "car_id", property = "car",
                    one = @One(select = "com.example.dao.CarDao.getCarById", fetchType = FetchType.EAGER))
    })
    List<RunningState> getAllCurrentRunnningStatesByUnitId(Integer unitId);

    /**
     * 查询某企业所有车辆当前运行状态
     * @param comId
     * @return
     */
    @Select("select rs2.rs_id,\n" +
            "medium.car_id,\n" +
            "rs2.rs_type,\n" +
            "rs2.rs_time,\n" +
            "rs2.rs_longitude,\n" +
            "rs2.rs_latitude,\n" +
            "rs2.rs_speed\n" +
            "from t_running_state as rs2 RIGHT JOIN\n" +
            "\t(select cars.car_id,max(rs1.rs_id) as max_rs_id from t_running_state rs1 RIGHT JOIN (\n" +
            "\t\tselect car_id \n" +
            "\t\tfrom \"i_car\"\n" +
            "\t\twhere com_id = #{comId}) as cars\n" +
            "\ton rs1.car_id = cars.car_id\n" +
            "\tGROUP BY cars.car_id) as medium\n" +
            "on rs2.rs_id = medium.max_rs_id")
    @Results({
            @Result(id = true, column = "rs_id", property = "rsId"),
            @Result(column = "car_id", property = "car",
                    one = @One(select = "com.example.dao.CarDao.getCarById", fetchType = FetchType.EAGER))
    })
    List<RunningState> getAllCurrentRunnningStatesByComId(Integer comId);
}
