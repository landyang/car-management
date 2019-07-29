package com.example.dao;

import com.example.bean.entity.RunningState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author HaN
 * @create 2019-04-27 11:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional//单元测试结束之后会自动回滚
public class RunningStateDaoTest {
    @Resource
    private RunningStateDao runningStateDao;

    @Test
    public void testGetCurrentRunnningStateByCarId() {
        Integer carId = 1;
        RunningState runningState = runningStateDao.getCurrentRunnningStateByCarId(carId);
        System.out.println(runningState);
    }

    @Test
    public void testGetAllCurrentRunnningStateByUnitId() {
        Integer unitId = 2;
        List<RunningState> list = runningStateDao.getAllCurrentRunnningStatesByUnitId(unitId);
        System.out.println(list);
    }

    @Test
    public void testGetRunningStateByCarIdAndTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<RunningState> list = runningStateDao.getRunningStatesByCarIdAndTime(1, Timestamp.valueOf("2019-04-27 09:31:27"), Timestamp.valueOf("2019-04-27 09:32:27"));
        System.out.println(list);
    }
}
