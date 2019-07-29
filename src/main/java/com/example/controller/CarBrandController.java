package com.example.controller;

import com.example.bean.dto.Result;
import com.example.bean.entity.CarBrand;
import com.example.enums.ResultCode;
import com.example.service.CarBrandService;
import com.example.utils.FileUtil;
import com.example.utils.ResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author HaN
 * @create 2019-03-31 14:23
 */
@RestController
@RequestMapping("/car")
@Api(value = "车辆品牌管理相关api")
public class CarBrandController {

    /**
     * 日志记录
     */
    private static final Logger log = LoggerFactory.getLogger(CarBrandController.class);

    @Autowired
    private CarBrandService carBrandServiceImpl;

    /**
     * 设置文件上传路径
     */
    @Value(value = "${upload-brandIcon-path}")
    private String filePath;

    /**
     * 添加车辆品牌
     *
     * @param fullname
     * @param simplename
     * @param synopsis   品牌简介 可不填
     * @param file
     * @return
     */
    @PostMapping("/insertCarBrand")
    @ApiOperation(value = "添加车辆品牌", notes = "品牌图标非必需字段")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "fullname", value = "品牌全称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "form", name = "simplename", value = "品牌简称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "form", name = "synopsis", value = "品牌简介", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "form", name = "file", value = "品牌图片", required = false, dataType = "MultipartFile")
    })
    public Result insertCarBrand(@RequestParam("fullname") String fullname,
                                 @RequestParam("simplename") String simplename,
                                 @RequestParam(value = "synopsis", required = false) String synopsis,
                                 @RequestParam(value = "file", required = false) MultipartFile file) {
        //        数据格式判断
        //        查重service层

        //实例化CarBrand
        CarBrand carBrand = new CarBrand();
        if (file != null) {
            //获取文件UUID名称，包含后缀
            String fileName = FileUtil.getUUIDName(file.getOriginalFilename());
            try {//上传文件
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);
                //log.info(filePath+fileName+"文件上传成功");
            } catch (IOException e) {//文件上传失败
                log.error("IO Exception when uploadind the file:" + fileName, e);
                return ResultUtil.warn(ResultCode.ERROR_FILE_UPLOAD_FAIL);
            } catch (Exception e) {
                log.error("Non IO Exception when uploadind the file:" + fileName, e);
                return ResultUtil.warn(ResultCode.ERROR_FILE_UPLOAD_FAIL);
            }
            carBrand.setBrandIcon(fileName);
        }


        carBrand.setFullname(fullname);
        carBrand.setSimplename(simplename);
        carBrand.setSynopsis(synopsis);
        //向数据库中添加车辆品牌
        int flag = carBrandServiceImpl.insertCarBrand(carBrand);
        if (flag == 1) {
            //检查插入结果
            return ResultUtil.success();
        } else if (flag == 0) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_FAIL);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 删除车辆品牌
     *
     * @param brandId
     * @return
     */
    @DeleteMapping("/deleteCarBrand/{id}")
    @ApiOperation(value = "删除车辆品牌", notes = "根据brandId删除")
    public Result deleteCarBrand(@ApiParam(value = "车辆品牌id", required = true) @PathVariable("id") Integer brandId) {
        //检查是否有该品牌车辆
        int countResult = carBrandServiceImpl.countCarByBrandId(brandId);
        if (countResult != 0) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_CASCADE,"存在该品牌车辆，不能删除！");
        }

        //删除车辆品牌数据
        int flag = carBrandServiceImpl.deleteCarBrand(brandId);
        //检查删除结果
        if (flag == 1) {
            /**
             *删除车辆品牌图片
             */
            //查询车辆品牌图片
            String brandIcon = carBrandServiceImpl.getBrandIconByBrandId(brandId);
            //如果车辆品牌图片不为空则删除图片
            if (brandIcon!=null) {
                String fileAbsolutePath = filePath + brandIcon;
                if (FileUtil.removeFile(fileAbsolutePath)) {
                    log.info(fileAbsolutePath + "删除成功");
                } else {
                    log.error(fileAbsolutePath + "删除失败");
                }
            }
            return ResultUtil.success();
        } else if (flag == 0) {
            //数据库操作失败
            return ResultUtil.warn(ResultCode.ERROR_DATA_FAIL);
        } else {
            //数据库异常
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 修改车辆品牌
     *
     * @param brandId
     * @param fullname
     * @param simplename
     * @param synopsis
     * @param file
     * @return
     */
    @PutMapping("updateCarBrand")
    @ApiOperation(value = "更新车辆品牌", notes = "品牌图标非必需字段")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "brandId", value = "品牌Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "form", name = "fullname", value = "品牌全称", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "form", name = "simplename", value = "品牌简称", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "form", name = "synopsis", value = "品牌简介", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "form", name = "file", value = "品牌图片", required = false, dataType = "MultipartFile")
    })
    public Result updateCarBrand(@RequestParam("brandId") Integer brandId,
                                 @RequestParam(value = "fullname", required = false) String fullname,
                                 @RequestParam(value = "simplename", required = false) String simplename,
                                 @RequestParam(value = "synopsis", required = false) String synopsis,
                                 @RequestParam(value = "file", required = false) MultipartFile file) {
        //        数据格式判断
        //        查重service层

        //实例化CarBrand
        CarBrand carBrand = new CarBrand();
        carBrand.setBrandId(brandId);
        carBrand.setFullname(fullname);
        carBrand.setSimplename(simplename);
        carBrand.setSynopsis(synopsis);
        //修改车辆图片
        if (file != null) {
            //获取文件UUID名称，包含后缀
            String fileName = FileUtil.getUUIDName(file.getOriginalFilename());
            try {//上传文件
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);
                //log.info(filePath+fileName+"文件上传成功");
            } catch (IOException e) {//文件上传失败
                log.error("IO Exception when uploadind the file:" + fileName, e);
                return ResultUtil.warn(ResultCode.ERROR_FILE_UPLOAD_FAIL);
            } catch (Exception e) {
                log.error("Non IO Exception when uploadind the file:" + fileName, e);
                return ResultUtil.warn(ResultCode.ERROR_FILE_UPLOAD_FAIL);
            }
            carBrand.setBrandIcon(fileName);
        }

        //查询车辆品牌图片
        String fileAbsolutePath = filePath + carBrandServiceImpl.getBrandIconByBrandId(brandId);
        //更新车辆品牌
        int flag = carBrandServiceImpl.updateCarBrand(carBrand);
        if (flag == 1) {//检查更新结果
            if (file != null) {
                //删除原车辆品牌图片
                if (FileUtil.removeFile(fileAbsolutePath)) {
                    log.info(fileAbsolutePath + "删除成功");
                } else {
                    log.error(fileAbsolutePath + "删除失败");
                }
            }
            return ResultUtil.success();
        } else if (flag == 0) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_FAIL);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 根据车辆品牌id查看品牌详情
     *
     * @param brandId
     * @return
     */
    @GetMapping("/getCarBrandById/{id}")
    @ApiOperation(value = "查看车辆品牌详情", notes = "根据车辆品牌id查看品牌详情")
    public Result getCarBrandById(@ApiParam(value = "车辆品牌id", required = true) @PathVariable("id") Integer brandId) {
        CarBrand carBrand = carBrandServiceImpl.getCarBrandById(brandId);
        if (carBrand != null) {//检查查询结果
            return ResultUtil.success(carBrand);
        } else if (carBrand == null) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_NOTFIND);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 获得全部车辆品牌及其所有型号
     *
     * @return
     */
    @GetMapping("/all-car-brand")
    @ApiOperation(value = "查询车辆品牌列表", notes = "获得全部车辆品牌及其所有型号")
    public Result allCarBrand() {
        List<CarBrand> carBrands = carBrandServiceImpl.allCarBrand();
        if (carBrands != null) {
            return ResultUtil.success(carBrands);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }


//    /**
//     * 车辆品牌列表（分页和模糊查询）(无用)
//     *
//     * @param carBrand
//     * @param pageNum  第几页，默认1
//     * @param pageSize 每页查询数量，默认6
//     * @return
//     */
//    @RequestMapping("/listCarBrand")
//    public Result listCarBrand(CarBrand carBrand,
//                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
//                               @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
//        PageInfo<CarBrand> pageInfo = carBrandServiceImpl.listCarBrand(carBrand, pageNum, pageSize);
//        if (pageInfo != null) {
//            return ResultUtil.success(pageInfo);
//        } else {
//            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
//        }
//    }
}
