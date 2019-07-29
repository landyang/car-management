package com.example.service;

import com.example.bean.entity.RunningState;

import java.sql.Timestamp;
import java.util.List;

/**
 * 车辆运行状态service接口
 *
 * @author HaN
 * @create 2019-04-27 11:45
 */
public interface RunningStateService {
    RunningState getCurrentRunnningStateByCarId(Integer carId);
    List<RunningState> getRunningStatesByCarIdAndTime(Integer carId, Timestamp beginTime, Timestamp endTime);
    List<RunningState> getAllCurrentRunnningStatesByUnitId(Integer unitId);
    List<RunningState> getAllCurrentRunnningStatesByComId(Integer comId);
}
