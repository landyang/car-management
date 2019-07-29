package com.example.service.impl;

import com.example.bean.entity.Car;
import com.example.bean.dto.CarPaging;
import com.example.dao.CarDao;
import com.example.service.CarService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 车辆service实现类
 *
 * @author HaN
 * @create 2019-03-28 22:07
 */
@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    /**
     * 添加车辆
     *
     * @param car
     * @return
     */
    @Override
    public Integer insertCar(Car car) {
        return carDao.insertCar(car);
    }

    /**
     * 批量更改车辆运营状态
     * @param state 状态
     * @param carIds 车辆id集合
     * @return
     */
    @Override
    public Integer updateOperationState(int state, List<Integer> carIds) {
        return carDao.updateOperationState(state,carIds);
    }

    /**
     * 根据id查询车辆
     *
     * @param carId
     * @return
     */
    @Override
    public Car getCarById(Integer carId) {
        return carDao.getCarById(carId);
    }

    /**
     * 动态更新车辆
     *
     * @param car
     * @return
     */
    @Override
    public Integer updateCar(Car car) {
        //字段更新 想设为空或者日期设为空可判断
        return carDao.updateCar(car);
    }

    /**
     * 根据企业id查询该企业下所有车辆
     * @param comId
     * @return
     */
    @Override
    public List<Car> listCarByComId(Integer comId) {
        return carDao.listCarByComId(comId);
    }

    /**
     * 分页动态查询全部车辆
     *
     * @param carPaging
     * @return
     */
    @Override
    public PageInfo<Car> listCar(CarPaging carPaging) {
        PageHelper.startPage(carPaging.getPageNum(), carPaging.getPageSize());//只要参数有值，自动分页
        List<Car> carList = carDao.listCar(carPaging);
        PageInfo<Car> pageInfo = new PageInfo<>(carList);
        return pageInfo;
    }

    /**
     * 判断某车辆是否被租用(未被租用返回0)
     * @param carId
     * @return
     */
    @Override
    public Integer judgingCarWhetherToRentOrNot(Integer carId) {
        return carDao.judgingCarWhetherToRentOrNot(carId);
    }

    /**
     * 通过车辆id集合批量查询车辆（用于批量违章，故不需要级联查询company和carModel）
     * @param carIds
     * @return
     */
    @Override
    public List<Car> listCarByIds(List<Integer> carIds) {
        return carDao.listCarByIds(carIds);
    }
}
