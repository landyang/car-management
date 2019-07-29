package com.example.dao;

import com.example.bean.entity.Car;
import com.example.bean.dto.CarPaging;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author HaN
 * @create 2019-03-31 17:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional//单元测试结束之后会自动回滚
public class CarDaoTest {
    @Resource
    private CarDao carDao;

    @Test
    public void testSelectCarById() {
        Car car = carDao.getCarById(1);
//        System.out.println(car.getCarModel().getModelId());
//        System.out.println(car);
        System.out.println(car==null);
        System.out.println(car);
        System.out.println(car.getCarLicense());
    }

    @Test
    public void testUpdateCar() {
        Car car = new Car();
        car.setCarId(1);
        car.setCarLicense("豫A77777");
        /*
        car.setCarVin("4P3CS34T1LE000001");
        CarModel carModel = new CarModel();
        carModel.setModelId(7);
        car.setCarModel(carModel);
        car.setEngineCode("652652M");
        Company company = new Company();
        company.setComId(1);
        car.setCompany(company);
        car.setCarLocation("郑州");
        car.setCarJointime(new Date());
        car.setMaintainTime(new Date());
        car.setCarInsurance("车险");
        car.setCarPrice(20000);
        car.setCarColor("白");
        car.setCarSeats(5);
        car.setCcpType("123456");
        car.setCcpCard("654321");
        car.setOperationState(1);*/
        int result = carDao.updateCar(car);
        System.out.println(result);
    }

    @Test
    public void testListCar() {
        CarPaging carPaging = new CarPaging();
        carPaging.setPageNum(1);
        carPaging.setPageSize(5);
        carPaging.setCarLicense("豫B");
        System.out.println(carDao.listCar(carPaging));
    }
}