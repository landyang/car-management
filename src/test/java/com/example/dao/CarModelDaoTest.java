package com.example.dao;

import com.example.bean.entity.CarBrand;
import com.example.bean.entity.CarModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author HaN
 * @create 2019-03-31 21:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional//单元测试结束之后会自动回滚
public class CarModelDaoTest {
    @Resource
    private CarModelDao carModelDao;

    @Test
    public void testInsertCarModel() {
        CarModel carModel = new CarModel();
        CarBrand carBrand = new CarBrand();
        carBrand.setBrandId(5);
        carModel.setCarBrand(carBrand);
        carModel.setModel("测试型号");
        carModel.setModelImage("测试图片");
        System.out.println(carModelDao.insertCarModel(carModel));
    }

    @Test
    public void testUpdateCarModel() {
        CarModel carModel = new CarModel();
        carModel.setModelId(12);
        CarBrand carBrand = new CarBrand();
        carBrand.setBrandId(5);
        carModel.setCarBrand(carBrand);
        carModel.setModel("测试修改型号");
        carModel.setModelImage("测试图片");
        System.out.println(carModelDao.updateCarModel(carModel));
    }

    @Test
    public void testSelectCarModelById() {
        CarModel carModel = carModelDao.getCarModelById(4);
//        System.out.println(carModel.getModel());
        System.out.println(carModel);
        System.out.println(carModel.getCarBrand().getFullname());
    }

    @Test
    public void testListCarModel() {
        CarModel carModel = new CarModel();
        CarBrand carBrand = new CarBrand();
        carBrand.setBrandId(6);
        carModel.setCarBrand(carBrand);
        System.out.println(carModelDao.listCarModel(carModel));
    }

    @Test
    public void testDeleteCarModel() {
        String str = new String("09a20088614b4e4491b36f0413b1ffff.png|db35ca1671ae485795e7e1e45bdeb15d.jpg|81e7e6e2a78045e480aaa9e9dacb3c89.png");
        String[] strs = str.split("\\|");
        for (String s : strs) {
            System.out.println(s);
        }
    }
}
