package com.example.controller;

import com.example.bean.dto.CarPaging;
import com.example.bean.dto.Result;
import com.example.bean.entity.Car;
import com.example.bean.entity.User;
import com.example.enums.CarOperationStateEnum;
import com.example.enums.ResultCode;
import com.example.enums.UserTypeEnum;
import com.example.service.CarService;
import com.example.service.UserService;
import com.example.utils.ResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaN
 * @create 2019-03-28 22:09
 */
@RestController
@RequestMapping("/car")
@Api(value = "车辆管理相关api")
public class CarController {
    @Autowired
    private CarService carServiceImpl;
    @Autowired
    private UserService userServiceImpl;

    /**
     * 添加车辆
     *
     * @param car
     * @return
     */
    @PostMapping("/insertCar")
    @ApiOperation(value = "添加车辆", notes = "运行状态默认为1")
    @ApiImplicitParam(paramType = "body",name = "car", value = "车辆实体类Car", required = true, dataType = "Car")
    public Result insertCar(@RequestBody Car car) {
//        数据格式判断
//        查重
//        连接车机，检查车机连接状态

        //设置车辆运行状态（数据库默认1）
        car.setOperationState(1);

        //插入数据
        int flag = carServiceImpl.insertCar(car);
        //检查结果
        if (flag == 1) {
            return ResultUtil.success();
        } else if (flag == 0) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_FAIL);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 批量修改车辆运营状态
     * @param state 运营状态：0停运 1正常
     * @param carIdsStr 车辆id集合
     * @return
     */
    @PutMapping("/updateOperationState")
    @ApiOperation(value = "批量修改车辆运营状态", notes = "0：停运 1：正常")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "state", value = "运营状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "carIdsStr", value = "车辆id集合字符串", required = true, dataType = "String")
    })
    public Result updateOperationState(@RequestParam("state") Integer state,
                                        @RequestParam("carIdsStr") String carIdsStr) {
        //拆分车辆id字符串carIdsStr
        String[] carIds = carIdsStr.split(",");
        //将车辆id数组carIds转成list集合listCarIds
        List<Integer> listCarIds = new ArrayList<>();
        for (int i = 0; i < carIds.length; i++) {
            listCarIds.add(Integer.parseInt(carIds[i]));
        }
        //判断要修改的运营状态，进行不同处理
        if (state == CarOperationStateEnum.NORMAL.getCode()) {
            //修改为正常运营状态
            Integer flag = carServiceImpl.updateOperationState(state, listCarIds);
            if (flag>=1) {
                return ResultUtil.success();
            } else if (flag == 0) {
                return ResultUtil.warn(ResultCode.ERROR_DATA_FAIL);
            } else {
                return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
            }
        } else if (state == CarOperationStateEnum.OUTAGE.getCode()) {
            //停运
            //正在被租用不能修改车辆集合
            List<Integer> failCarIds = new ArrayList<>();
            for (Integer listCarId : listCarIds) {
                //遍历判断车辆是否被租用
                int judgeNum = carServiceImpl.judgingCarWhetherToRentOrNot(listCarId);
                if (judgeNum != 0) {
                    //正在被租用
                    failCarIds.add(listCarId);
                    listCarIds.remove(listCarId);
                }
            }
            //修改运营状态
            Integer flag = carServiceImpl.updateOperationState(state, listCarIds);
            if (flag>=1) {
                if (failCarIds.isEmpty()) {
                    return ResultUtil.success();
                } else {
                    return ResultUtil.success(ResultCode.ERROR_PARTIAL_FAILURE,"部分车辆正在租用中！",failCarIds);
                }
            } else if (flag == 0) {
                return ResultUtil.warn(ResultCode.ERROR_DATA_FAIL);
            } else {
                return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
            }
        } else {
            //要修改的状态不存在
            return ResultUtil.warn(ResultCode.ERROR_DATA_NOTACCORD,"要修改的状态不存在！");
        }
    }

    /**
     * 根据车辆id查看车辆详情
     *
     * @param carId 车辆id
     * @return
     */
    @GetMapping("/getCarById/{id}")
    @ApiOperation(value = "查询车辆详情", notes = "根据id查询")
    public Result getCarById(@ApiParam(value = "车辆id", required = true) @PathVariable("id") Integer carId) {
        Car car = carServiceImpl.getCarById(carId);
        if (car != null) {//检查查询结果
            return ResultUtil.success(car);
        } else if (car == null) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_NOTFIND);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 更新车辆
     *
     * @param car
     * @return
     */
    @PutMapping("/updateCar")
    @ApiOperation(value = "更新车辆")
    @ApiImplicitParam(paramType = "body",name = "car", value = "车辆实体类Car", required = true, dataType = "Car")
    public Result updateCar(@RequestBody Car car) {
//        数据格式判断
//        查重
/*//        是否修改车机
        if (car.getCcpCard()!=null){

        }*/

        int flag = carServiceImpl.updateCar(car);
        if (flag == 1) {//修改数据，检查结果
            return ResultUtil.success();
        } else if (flag == 0) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_FAIL);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 根据用户id查询该用户所属企业下所有车辆
     *
     * @param userId
     * @return
     */
    @GetMapping("/list-car/{id}")
    @ApiOperation(value = "查询所有车辆", notes = "根据用户id查询该用户所属企业下所有车辆")
    public Result listCarByUserId(@ApiParam(value = "用户id", required = true) @PathVariable("id") Integer userId) {
        //获取登录用户信息
        User user = userServiceImpl.getUserById(userId);
        if (UserTypeEnum.COMPANY_USER.getCode() == user.getUserType()) {
            //如果是企业用户
            List<Car> cars = carServiceImpl.listCarByComId(user.getUserUnit());
            //检查查询结果
            if (cars != null) {
                return ResultUtil.success(cars);
            } else {
                return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
            }
        } else {
            //如果是单位用户或者其他（则没有权限调用）
            return ResultUtil.warn(ResultCode.ERROR_UNACCREDITED);
        }
    }





    /**
     * 车辆列表（分页和模糊查询）
     *
     * @param carPaging
     * @return
     */
    @GetMapping("/listCar")
    @ApiOperation(value = "车辆列表（分页和模糊查询）已弃用", notes = "已弃用")
    public Result listCar(@RequestBody CarPaging carPaging) {
        PageInfo<Car> pageInfo = carServiceImpl.listCar(carPaging);
        if (pageInfo != null) {//检查查询结果
            return ResultUtil.success(pageInfo);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }
}
