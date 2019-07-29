package com.example.service;

import com.example.bean.entity.Car;
import com.example.bean.dto.CarPaging;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 车辆service接口
 *
 * @author HaN
 * @create 2019-03-28 22:07
 */
public interface CarService {
    /**
     * 添加车辆
     * @param car
     * @return
     */
    Integer insertCar(Car car);

    /**
     * 批量更改车辆运营状态
     * @param state 状态
     * @param carIds 车辆id集合
     * @return
     */
    Integer updateOperationState(@Param("state") int state, @Param("carIds") List<Integer> carIds);

    /**
     * 根据车辆id查找车辆
     * @param carId
     * @return
     */
    Car getCarById(Integer carId);

    /**
     * 动态修改车辆
     * @param car
     * @return
     */
    Integer updateCar(Car car);

    /**
     * 根据企业id查询该企业下所有车辆
     * @param comId
     * @return
     */
    List<Car> listCarByComId(Integer comId);

    /**
     * 动态分页查询车辆列表
     * @param carPaging
     * @return
     */
    PageInfo<Car> listCar(CarPaging carPaging);

    /**
     *判断某车辆是否被租用(未被租用返回0)
     * @param carId
     * @return
     */
    Integer judgingCarWhetherToRentOrNot(Integer carId);

    /**
     * 通过车辆id集合批量查询车辆（用于批量违章，故不需要级联查询company和carModel）
     * @param carIds
     * @return
     */
    List<Car> listCarByIds(List<Integer> carIds);
}
