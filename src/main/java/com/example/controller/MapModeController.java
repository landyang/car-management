package com.example.controller;

import com.example.bean.dto.MapMenuTree;
import com.example.bean.dto.Result;
import com.example.bean.entity.RunningState;
import com.example.bean.entity.User;
import com.example.enums.ResultCode;
import com.example.enums.UserTypeEnum;
import com.example.service.MenuService;
import com.example.service.RunningStateService;
import com.example.service.UserService;
import com.example.utils.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * 地图模式控制器
 *
 * @author HaN
 * @create 2019-04-27 11:44
 */
@RestController
@RequestMapping("/map")
@Api(value = "地图模式相关api")
public class MapModeController {

    @Autowired
    private RunningStateService runningStateServiceImpl;
    @Autowired
    private UserService userServiceImpl;
    @Autowired
    private MenuService menuServiceImpl;

    /**
     * 根据用户id获取权限下的所有车辆当前运行状态列表
     * @param userId 登录用户id
     * @return
     */
    @GetMapping("/allRS/{id}")
    @ApiOperation(value = "获取所有车辆当前运行状态列表", notes = "根据用户id获取权限下的所有车辆当前运行状态列表")
    public Result getAllRunningStatesByUserId(@ApiParam(value = "用户id", required = true) @PathVariable("id") Integer userId) {
        //获取登录用户信息
        User user = userServiceImpl.getUserById(userId);
        if (UserTypeEnum.UNIT_USER.getCode() == user.getUserType()) {
            //如果是单位用户
            List<RunningState> runningStates = runningStateServiceImpl.getAllCurrentRunnningStatesByUnitId(user.getUserUnit());
            if (runningStates != null) {
                //检查查询结果
                return ResultUtil.success(runningStates);
            } else {
                return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
            }
        } else if (UserTypeEnum.COMPANY_USER.getCode() == user.getUserType()) {
            //如果是企业用户
            List<RunningState> runningStates = runningStateServiceImpl.getAllCurrentRunnningStatesByComId(user.getUserUnit());
            //检查查询结果
            if (runningStates != null) {
                return ResultUtil.success(runningStates);
            } else {
                return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
            }
        } else {//没有权限
            return ResultUtil.warn(ResultCode.ERROR_UNACCREDITED);
        }
    }

    /**
     * 根据车辆id查看当前车辆运行状态
     *
     * @param carId
     * @return
     */
    @GetMapping("/getCRS/{id}")
    @ApiOperation(value = "查看当前车辆运行状态", notes = "根据车辆id查看当前车辆运行状态")
    public Result getCurrentRunnningStateByCarId(@ApiParam(value = "车辆id", required = true) @PathVariable("id") Integer carId) {
        RunningState runningState = runningStateServiceImpl.getCurrentRunnningStateByCarId(carId);
        //检查查询结果
        if (runningState != null) {
            return ResultUtil.success(runningState);
        } else if (runningState == null) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_NOTFIND);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 查询指定车辆某一时间段的所有运行状态（运行轨迹）
     * @param carId
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/getRSByIdAndTime")
    @ApiOperation(value = "运行轨迹", notes = "查询指定车辆某一时间段的所有运行状态（运行轨迹）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "carId", value = "车辆Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "form", name = "beginTime", value = "开始时间", required = false, dataType = "Timestamp"),
            @ApiImplicitParam(paramType = "form", name = "endTime", value = "结束时间", required = false, dataType = "Timestamp")
})
    public Result getRunningStatesByCarIdAndTime(Integer carId, Timestamp beginTime, Timestamp endTime) {
        List<RunningState> runningStates = runningStateServiceImpl.getRunningStatesByCarIdAndTime(carId, beginTime, endTime);
        //检查查询结果
        if (runningStates != null) {
            return ResultUtil.success(runningStates);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 地图模式 省市县树形菜单查询（MapMenuTree格式）：
     * @return
     */
    @GetMapping("/map-menu")
    @ApiOperation(value = "省市县树形菜单查询", notes = "省市县树形菜单查询")
    public Result getMapMenuList() {
        List<MapMenuTree> resultList = menuServiceImpl.getMapMenuList();
        //检查查询结果
        if (resultList != null) {
            return ResultUtil.success(resultList);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 地图模式 根据地区查询该地区全部单位租车状况（MapMenuTree格式）：
     * @return
     */
    @GetMapping("/unit-menu/{areacode}")
    @ApiOperation(value = "根据地区查询该地区全部单位租车状况", notes = "根据地区查询该地区全部单位租车状况")
    public Result getUnitMenuListByAreacode(@PathVariable("areacode") String areacode) {
        List<MapMenuTree> resultList = menuServiceImpl.getUnitMenuListByAreacode(areacode);
        //检查查询结果
        if (resultList != null) {
            return ResultUtil.success(resultList);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 地图模式 根据单位id查询该单位租的所有车辆当前运行状态列表
     * @return
     */
    @GetMapping("/unit-all-rs/{id}")
    @ApiOperation(value = "查询单位租的所有车辆当前运行状态列表", notes = "根据单位id查询该单位租的所有车辆当前运行状态列表")
    public Result getAllRunningStatesByUnitId(@PathVariable("id") Integer unitId) {
        //如果是单位用户
        List<RunningState> runningStates = runningStateServiceImpl.getAllCurrentRunnningStatesByUnitId(unitId);
        if (runningStates != null) {
            //检查查询结果
            return ResultUtil.success(runningStates);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }
}
