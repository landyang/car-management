package com.example.dao;

import com.example.bean.entity.Car;
import com.example.bean.entity.Peccancy;
import com.example.utils.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HaN
 * @create 2019-05-09 12:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional//单元测试结束之后会自动回滚
public class PeccancyDaoTest {
    @Resource
    private PeccancyDao peccancyDao;
    /**
     * 日志记录
     */
    private static Logger log = LoggerFactory.getLogger(PeccancyDaoTest.class);

    @Test
    public void testInsertPeccancies() {
        //测试结果，只要有更新不管更新几条数据返回值都是1，如果没有更新返回0
        List<Peccancy> peccancies = new ArrayList<Peccancy>();
        /*Car car = new Car();
        car.setCarId(30);
        Peccancy peccancy1 = new Peccancy();
        peccancy1.setCar(car);
        peccancy1.setDate(Timestamp.valueOf("2017-10-04 20:22:33"));
        peccancy1.setArea("番禺大道富华东路路口");
        peccancy1.setAct("机动车通过有灯控路口时，不按所需行进方向驶入导向车道的");
        peccancy1.setCode("4401267902030070");
        peccancy1.setFen("2");
        peccancy1.setMoney("100");
        peccancy1.setHandled("0");
        peccancy1.setWzcity("广东广州");

        Peccancy peccancy2 = new Peccancy();
        peccancy2.setCar(car);
        peccancy2.setDate(Timestamp.valueOf("2017-10-04 20:22:33"));
        peccancy2.setArea("番禺大道富华东路路口");
        peccancy2.setAct("机动车通过有灯控路口时，不按所需行进方向驶入导向车道的");
        peccancy2.setCode("4401267902030070");
        peccancy2.setFen("2");
        peccancy2.setMoney("100");
        peccancy2.setHandled("0");
        peccancy2.setWzcity("广东广州");

        peccancies.add(peccancy1);
        peccancies.add(peccancy2);

        *//*int flag = peccancyDao.insertPeccancies(peccancies);
        System.out.println(flag);

        System.out.println(flag==1 || flag==0);*//*
        for (int i = 0; i < 10; i++) {
            int flag = peccancyDao.insertPeccancies(peccancies);
            if (flag==1 || flag==0) {
                System.out.println(i);
                break;
            }
        }*/

        try {
            Integer flag = peccancyDao.insertPeccancies(peccancies);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetAllPeccanciesByComId() {
        List<Peccancy> peccancies = peccancyDao.getAllPeccanciesByComId(10000);
        System.out.println(peccancies == null);
        System.out.println(peccancies.isEmpty());
        System.out.println(peccancies);
        List<Peccancy> peccancies2 = new ArrayList<>();
        System.out.println(peccancies2 == null);
        System.out.println(peccancies2.isEmpty());
        System.out.println(peccancies2);
        peccancies2=null;
        System.out.println(peccancies2);

    }

    @Test
    public void testUpdatePeccanciesHandledByCompanyOrUnit() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        Integer flag = peccancyDao.updatePeccanciesHandledByCompany(ids);
        System.out.println(flag);

        Integer[] idsArr = {};
        System.out.println(idsArr.length);
    }

}
