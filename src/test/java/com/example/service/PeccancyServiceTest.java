package com.example.service;

import com.example.bean.entity.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HaN
 * @create 2019-05-09 17:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional//单元测试结束之后会自动回滚
public class PeccancyServiceTest {
    @Resource
    private PeccancyService peccancyServiceImpl;
    @Resource
    private CarService carServiceImpl;
    @Value(value = "${juhe-app-key}")
    private String APPKEY;

    @Test
    public void testInsertPeccancies() {
        List<Integer> carIds = new ArrayList<>();
        carIds.add(1);
        List<Car> cars = carServiceImpl.listCarByIds(carIds);
        for (int i = 0; i < cars.size(); i++) {
            Car car =  cars.get(i);
            //获取车牌号
            String hphm = car.getCarLicense();
            //获取发动机号后6位
            String engineCode = car.getEngineCode();
            int engineCodeLength = engineCode.length();
            String engineno = null;
            if (engineCodeLength>6) {
                engineno = engineCode.substring(engineCodeLength-6,engineCodeLength);
            } else {
                engineno = engineCode;
            }
            //获取车架号后6位
            String carVin = car.getCarVin();
            int carVinLength = carVin.length();
            String classno = carVin.substring(carVinLength-6,carVinLength);
            //拼接请求字符串
            String param = "hphm={0}&engineno={1}&classno={2}&key=8{3}";
            param = MessageFormat.format(param,hphm,engineno,classno,APPKEY);
            System.out.println(param);
        }
    }

    @Test
    public void testHowManyJuheRequstLimit() {
        Integer num = peccancyServiceImpl.howManyJuheRequstLimit();
        System.out.println(num);
    }
}
