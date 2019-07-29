package com.example.service;

import com.example.bean.entity.Car;
import com.example.bean.dto.CarPaging;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.INTERNAL;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HaN
 * @create 2019-04-03 20:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional//单元测试结束之后会自动回滚
public class CarServiceTest {

    @Resource
    private CarService carServiceImpl;

    @Test
    public void testListCar() {
        CarPaging carPaging = new CarPaging();
        carPaging.setPageNum(1);
        carPaging.setPageSize(2);
        carPaging.setCarLicense("豫A");
        PageInfo<Car> pageInfo = carServiceImpl.listCar(carPaging);
        System.out.println(pageInfo != null);
        System.out.println(pageInfo);
    }

    @Test
    public void testListCarByIds() {
        List<Integer> carIds = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            carIds.add(i);
        }

        List<Car> cars = carServiceImpl.listCarByIds(carIds);
        System.out.println(cars);
    }
}
