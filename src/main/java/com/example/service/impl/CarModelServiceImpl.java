package com.example.service.impl;

import com.example.bean.entity.CarModel;
import com.example.dao.CarModelDao;
import com.example.service.CarModelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author HaN
 * @create 2019-03-30 17:28
 */
@Service
@Transactional
public class CarModelServiceImpl implements CarModelService {
    @Autowired
    private CarModelDao carModelDao;

    @Override
    public Integer insertCarModel(CarModel carModel) {
        return carModelDao.insertCarModel(carModel);
    }

    @Override
    public Integer deleteCarModel(Integer modelId) {
        return carModelDao.deleteCarModel(modelId);
    }

    @Override
    public Integer updateCarModel(CarModel carModel) {
        return carModelDao.updateCarModel(carModel);
    }

    @Override
    public CarModel getCarModelById(Integer modelId) {
        return carModelDao.getCarModelById(modelId);
    }

    @Override
    public PageInfo<CarModel> listCarModel(CarModel carModel, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CarModel> carModelList = carModelDao.listCarModel(carModel);
        PageInfo<CarModel> pageInfo = new PageInfo<>(carModelList);
        return pageInfo;
    }

    @Override
    public List<CarModel> allCarModel() {
        return carModelDao.allCarModel();
    }

    @Override
    public Integer countCarByModelId(Integer modelId) {
        return carModelDao.countCarByModelId(modelId);
    }

    @Override
    public String getModelImageByModelId(Integer modelId) {
        return carModelDao.getModelImageByModelId(modelId);
    }
}
