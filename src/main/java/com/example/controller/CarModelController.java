package com.example.controller;

import com.example.bean.entity.CarBrand;
import com.example.bean.entity.CarModel;
import com.example.enums.ResultCode;
import com.example.service.CarModelService;
import com.example.utils.FileUtil;
import com.example.bean.dto.Result;
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
 * @create 2019-04-02 21:32
 */
@RestController
@RequestMapping("/car")
@Api(value = "车辆型号管理相关api")
public class CarModelController {

    //日志记录
    private static final Logger log = LoggerFactory.getLogger(CarModelController.class);

    @Autowired
    private CarModelService carModelServiceImpl;

    /**
     * 设置文件上传路径
     */
    @Value(value = "${upload-modelImage-path}")
    private String filePath;

    /**
     * 添加车辆型号
     *
     * @param brandId
     * @param model
     * @param files
     * @return
     */
    @PostMapping("/insertCarModel")
    @ApiOperation(value = "添加车辆型号", notes = "型号图标为必需字段")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "brandId", value = "车辆品牌id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "form", name = "model", value = "车辆型号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "form", name = "files", value = "型号图片", required = true, dataType = "MultipartFile[]")
    })
    public Result insertCarModel(@RequestParam("brandId") Integer brandId,
                                 @RequestParam("model") String model,
                                 @RequestParam(value = "files",required = false) MultipartFile[] files) {
        //        数据格式判断
        if (files == null || files.length == 0) {//如果未上传文件 或者 文件为空则返回文件为空信息
            return ResultUtil.warn(ResultCode.ERROR_FILE_ISEMPTY);
        }
        //        查重service层

        StringBuffer modelImage = new StringBuffer();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
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
            if (i == files.length - 1) {//拼接多文件地址
                modelImage.append(fileName);
            } else {
                modelImage.append(fileName + "|");
            }
        }

        //实例化CarModel
        CarModel carModel = new CarModel();
        //实例化CarBrand
        CarBrand carBrand = new CarBrand();
        carBrand.setBrandId(brandId);
        carModel.setCarBrand(carBrand);
        carModel.setModel(model);
        carModel.setModelImage(modelImage.toString());
        //向数据库中添加车辆品牌
        int flag = carModelServiceImpl.insertCarModel(carModel);
        if (flag == 1) {//检查插入结果
            return ResultUtil.success();
        } else if (flag == 0) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_FAIL);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 删除车辆型号
     *
     * @param modelId
     * @return
     */
    @DeleteMapping("/deleteCarModel/{id}")
    @ApiOperation(value = "删除车辆型号", notes = "根据modelId删除")
    public Result deleteCarModel(@ApiParam(value = "车辆型号id", required = true) @PathVariable("id") Integer modelId) {
        //检查是否有该型号车辆
        int countResult = carModelServiceImpl.countCarByModelId(modelId);
        if (countResult != 0) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_CASCADE);
        }
        //查询车辆型号全部图片
        String fileAbsolutePaths = carModelServiceImpl.getModelImageByModelId(modelId);
        //删除车辆型号数据
        int flag = carModelServiceImpl.deleteCarModel(modelId);
        if (flag == 1) {//检查删除结果
            //删除车辆型号图片
            String[] fileAbsolutePathsArr = fileAbsolutePaths.split("\\|");//根据|分割车辆型号图片
            for (String s : fileAbsolutePathsArr) {//遍历删除车辆型号图片
                String fileAbsolutePath = filePath + s;
                if (FileUtil.removeFile(fileAbsolutePath)) {
                    log.info(fileAbsolutePath + "删除成功");
                } else {
                    log.error(fileAbsolutePath + "删除失败");
                }
            }
            return ResultUtil.success();
        } else if (flag == 0) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_FAIL);//数据库操作失败（无该车辆型号）
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);//数据库异常
        }
    }

    /**
     * 修改车辆型号
     *
     * @param modelId
     * @param brandId
     * @param model
     * @param files
     * @return
     */
    @PutMapping("updateCarModel")
    @ApiOperation(value = "更新车辆型号", notes = "型号图标非必需字段")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "modelId", value = "型号Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "form", name = "brandId", value = "品牌id", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "form", name = "model", value = "型号", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "form", name = "files", value = "型号图片", required = false, dataType = "MultipartFile[]")
    })
    public Result updateCarBrand(@RequestParam("modelId") Integer modelId,
                                 @RequestParam(value = "brandId", required = false) Integer brandId,
                                 @RequestParam(value = "model", required = false) String model,
                                 @RequestParam(value = "files", required = false) MultipartFile[] files) {
        //        数据格式判断
        //        查重service层

        //实例化CarModel
        CarModel carModel = new CarModel();
        carModel.setModelId(modelId);
        //实例化CarBrand
        CarBrand carBrand = new CarBrand();
        carBrand.setBrandId(brandId);
        carModel.setCarBrand(carBrand);
        carModel.setModel(model);
        //修改车辆型号图片
        if (files.length != 0) {//判断是否修改车辆型号图片
            StringBuffer modelImage = new StringBuffer();
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
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
                if (i == files.length - 1) {//拼接多文件地址
                    modelImage.append(fileName);
                } else {
                    modelImage.append(fileName + "|");
                }
            }
            carModel.setModelImage(modelImage.toString());
        }

        //查询车辆型号全部图片
        String fileAbsolutePaths = carModelServiceImpl.getModelImageByModelId(modelId);
        //更新车辆品牌
        int flag = carModelServiceImpl.updateCarModel(carModel);
        if (flag == 1) {//检查更新结果
            if (files.length != 0) {//判断是否修改车辆型号图片
                //删除车辆型号图片
                String[] fileAbsolutePathsArr = fileAbsolutePaths.split("\\|");//根据|分割车辆型号图片
                for (String s : fileAbsolutePathsArr) {//遍历删除车辆型号图片
                    String fileAbsolutePath = filePath + s;
                    if (FileUtil.removeFile(fileAbsolutePath)) {
                        log.info(fileAbsolutePath + "删除成功");
                    } else {
                        log.error(fileAbsolutePath + "删除失败");
                    }
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
     * 根据车辆型号id查看型号详情
     *
     * @param modelId
     * @return
     */
    @GetMapping("/getCarModelById/{id}")
    @ApiOperation(value = "查看车辆型号详情", notes = "根据车辆型号id查看型号详情")
    public Result getCarModelById(@ApiParam(value = "车辆型号id", required = true) @PathVariable("id") Integer modelId) {
        CarModel carModel = carModelServiceImpl.getCarModelById(modelId);
        if (carModel != null) {//检查查询结果
            return ResultUtil.success(carModel);
        } else if (carModel == null) {
            return ResultUtil.warn(ResultCode.ERROR_DATA_NOTFIND);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }

    /**
     * 全部车辆型号列表
     *
     * @return
     */
    @GetMapping("/all-car-model")
    @ApiOperation(value = "查询车辆型号列表", notes = "获得全部车辆型号")
    public Result allCarModel() {
        List<CarModel> carModels = carModelServiceImpl.allCarModel();
        if (carModels != null) {
            return ResultUtil.success(carModels);
        } else {
            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
        }
    }


//    /**
//     * 车辆型号列表（分页和模糊查询）（无用）
//     *
//     * @param carModel
//     * @param pageNum  第几页，默认1
//     * @param pageSize 每页查询数量，默认6
//     * @return
//     */
//    @RequestMapping("/listCarModel")
//    public Result listCarModel(CarModel carModel,
//                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
//                               @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
//        PageInfo<CarModel> pageInfo = carModelServiceImpl.listCarModel(carModel, pageNum, pageSize);
//        if (pageInfo != null) {
//            return ResultUtil.success(pageInfo);
//        } else {
//            return ResultUtil.warn(ResultCode.ERROR_DATA_ABNORMAL);
//        }
//    }
}
