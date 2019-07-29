package com.example.dao;

import com.example.bean.entity.CarBrand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HaN
 * @create 2019-03-31 21:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional//单元测试结束之后会自动回滚
public class CarBrandDaoTest {

    @Resource
    private CarBrandDao carBrandDao;

    @Test
    public void testGetCarBrandById() {
        CarBrand carBrand = carBrandDao.getCarBrandById(5);
        System.out.println(carBrand);
    }

    @Test
    public void testListCarBrand() {
        CarBrand carBrand = new CarBrand();
        carBrand.setFullname("汽车");
        List<CarBrand> carBrands = carBrandDao.listCarBrand(carBrand);
        System.out.println(carBrands);
    }

    @Test
    public void testInsertCarBrand() {
        CarBrand carBrand = new CarBrand();
        carBrand.setFullname("TestUtil");
        carBrand.setBrandIcon("TestUtil");
        carBrand.setSimplename("TestUtil");
        Integer integer = carBrandDao.insertCarBrand(carBrand);
        System.out.println(integer);
    }

    @Test
    public void testCountCarByBrandId() {
        System.out.println(carBrandDao.countCarByBrandId(7));
    }
}
