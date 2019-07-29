package com.example.service;

import com.example.bean.entity.CarModel;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author HaN
 * @create 2019-03-30 17:27
 */
public interface CarModelService {
    Integer insertCarModel(CarModel carModel);

    Integer deleteCarModel(Integer modelId);

    Integer updateCarModel(CarModel carModel);

    CarModel getCarModelById(Integer modelId);

    PageInfo<CarModel> listCarModel(CarModel carModel, Integer pageNum, Integer pageSize);

    List<CarModel> allCarModel();

    Integer countCarByModelId(Integer modelId);

    String getModelImageByModelId(Integer modelId);
}
