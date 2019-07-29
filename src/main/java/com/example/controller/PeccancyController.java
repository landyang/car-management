package com.example.controller;

import com.example.bean.dto.Result;
import com.example.bean.entity.Peccancy;
import com.example.bean.entity.RunningState;
import com.example.bean.entity.User;
import com.example.enums.ResultCode;
import com.example.enums.UserTypeEnum;
import com.example.exception.JuheRequestException;
import com.example.exception.ListCarByIdsException;
import com.example.service.PeccancyService;
import com.example.service.UserService;
import com.example.service.impl.PeccancyServiceImpl;
import com.example.utils.ResultUtil;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 车辆违章控制器
 *
 * @author HaN
 * @create 2019-05-07 19:21
 */
@RestController
@RequestMapping("/peccancy")
@Api(value = "违章管理相关api")
public class PeccancyController {
    @Autowired
    private PeccancyService peccancyServiceImpl;
    @Autowired
    private UserService userServiceImpl;
    /**
     * 日志记录
     */
    private static final Logger log = LoggerFactory.getLogger(PeccancyController.class);

    /**
     * 批量查询车辆违章并更新到数据库
     * @param carIdsStr
     * @return
     */
    @RequestMapping("/insertPeccancies")
    public Result insertPeccancies(@RequestParam(value = "carIdsStr") String carIdsStr) {
        //拆分车辆id字符串carIdsStr
        String[] carIds = carIdsStr.split(",");
        try {
            //将车辆id数组carIds转成list集合listCarIds
            List<Integer> listCarIds = new ArrayList<>();
            for (int i = 0; i < carIds.length; i++) {
                listCarIds.add(Integer.parseInt(carIds[i]));
            }

            Set<Integer> flag = peccancyServiceImpl.insertPeccancies(listCarIds);
            if (flag.isEmpty()) {
                return ResultUtil.success("违章查询并添加数据库成功");
            } else {
                return ResultUtil.warn(ResultCode.ERROR_PARTIAL_FAILURE,"部分车辆违章查询或添加数据库失败",flag);
            }
        } catch (ListCarByIdsException e) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_FAIL,e.getMessage());
        } catch (Exception e) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL,e.getMessage());
        }
    }

    /**
     * 聚合全国车辆违章接口剩余请求次数查询
     * @return
     */
    @RequestMapping("/juheRequstLimit")
    public Result getHowManyJuheRequstLimit() {
        Integer num = null;
        try {
            num = peccancyServiceImpl.howManyJuheRequstLimit();
            if (num!=null) {
                return ResultUtil.success(num);
            } else {
                return ResultUtil.warn(ResultCode.WARN,"请求聚合数据接口调用失败");
            }
        } catch (JuheRequestException e) {
            //请求超过次数限制：剩余次数为0
            return ResultUtil.success(0);
        }
    }

    /**
     * 根据用户id获取权限下的所有车辆违章列表
     * @param userId
     * @return
     */
    @RequestMapping("/allPeccancies/{id}")
    public Result getAllPeccanciesByByUserId(@PathVariable("id") Integer userId) {
        //获取登录用户信息
        User user = userServiceImpl.getUserById(userId);
        if (UserTypeEnum.UNIT_USER.getCode() == user.getUserType()) {
            //如果是单位用户
            List<Peccancy> peccancies = peccancyServiceImpl.getAllPeccanciesByUnitId(user.getUserUnit());
            if (peccancies != null) {
                //检查查询结果
                return ResultUtil.success(peccancies);
            } else {
                return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
            }
        } else if (UserTypeEnum.COMPANY_USER.getCode() == user.getUserType()) {
            //如果是企业用户
            List<Peccancy> peccancies = peccancyServiceImpl.getAllPeccanciesByComId(user.getUserUnit());
            //检查查询结果
            if (peccancies != null) {
                return ResultUtil.success(peccancies);
            }  else {
                return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
            }
        } else {//没有权限
            return ResultUtil.warn(ResultCode.ERROR_UNACCREDITED);
        }
    }

    /**
     * 根据用户id批量修改违章状态（）
     * 单位用户修改违章状态(待企业用户审核)
     * 企业用户最终修改违章状态
     *      hanled 违章状态  0 未处理 1 已处理待审核 2 已处理
     * 错误参数：ids不能为空
     * @param userId
     * @return
     */
    /*@RequestMapping("/updatePeccanciesHandled")
    public Result updatePeccanciesHandledByUserId(@RequestParam("userId") Integer userId,@RequestParam("idsStr") String idsStr) {
        //拆分违章id字符串idsStr
        String[] ids = idsStr.split(",");
        //将违章id数组ids转成list集合listIds
        List<Integer> listIds = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            listIds.add(Integer.parseInt(ids[i]));
        }
        //获取登录用户信息
        User user = userServiceImpl.getUserById(userId);
        if (UserTypeEnum.UNIT_USER.getCode() == user.getUserType()) {
            //如果是单位用户
            Integer flag = peccancyServiceImpl.updatePeccanciesHandledByUnit(listIds);
            //检查更新结果
            if (flag >= 0) {
                return ResultUtil.success();
            } else {
                return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
            }
        } else if (UserTypeEnum.COMPANY_USER.getCode() == user.getUserType()) {
            //如果是企业用户
            Integer flag = peccancyServiceImpl.updatePeccanciesHandledByCompany(listIds);
            //检查更新结果
            if (flag >= 0) {
                return ResultUtil.success();
            }  else {
                return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
            }
        } else {//没有权限
            return ResultUtil.warn(ResultCode.ERROR_UNACCREDITED);
        }
    }*/


    @RequestMapping("/updatePeccanciesHandled")
    public Result updatePeccanciesHandledByUserId(@RequestParam("userId") Integer userId,@RequestParam("ids") Integer[] ids) {
        //将违章id数组ids转成list集合listIds
        List<Integer> listIds = new ArrayList<>(Arrays.asList(ids));
        //获取登录用户信息
        User user = userServiceImpl.getUserById(userId);
        if (UserTypeEnum.UNIT_USER.getCode() == user.getUserType()) {
            //如果是单位用户
            Integer flag = peccancyServiceImpl.updatePeccanciesHandledByUnit(listIds);
            //检查更新结果
            if (flag >= 0) {
                return ResultUtil.success();
            } else {
                return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
            }
        } else if (UserTypeEnum.COMPANY_USER.getCode() == user.getUserType()) {
            //如果是企业用户
            Integer flag = peccancyServiceImpl.updatePeccanciesHandledByCompany(listIds);
            //检查更新结果
            if (flag >= 0) {
                return ResultUtil.success();
            }  else {
                return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
            }
        } else {//没有权限
            return ResultUtil.warn(ResultCode.ERROR_UNACCREDITED);
        }
    }
}
