package com.example.service.impl;

import com.example.bean.entity.RunningState;
import com.example.dao.RunningStateDao;
import com.example.service.RunningStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * 车辆运行状态service实现类
 *
 * @author HaN
 * @create 2019-04-27 11:46
 */
@Service
@Transactional
public class RunningStateServiceImpl implements RunningStateService {
    @Autowired
    private RunningStateDao runningStateDao;

    @Override
    public RunningState getCurrentRunnningStateByCarId(Integer carId) {
        return runningStateDao.getCurrentRunnningStateByCarId(carId);
    }

    @Override
    public List<RunningState> getRunningStatesByCarIdAndTime(Integer carId, Timestamp beginTime, Timestamp endTime) {
        return runningStateDao.getRunningStatesByCarIdAndTime(carId,beginTime,endTime);
    }

    @Override
    public List<RunningState> getAllCurrentRunnningStatesByUnitId(Integer unitId) {
        return runningStateDao.getAllCurrentRunnningStatesByUnitId(unitId);
    }

    @Override
    public List<RunningState> getAllCurrentRunnningStatesByComId(Integer comId) {
        return runningStateDao.getAllCurrentRunnningStatesByComId(comId);
    }
}
