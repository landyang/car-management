package com.example.service.impl;

import com.example.bean.entity.CarBrand;
import com.example.dao.CarBrandDao;
import com.example.service.CarBrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author HaN
 * @create 2019-03-30 17:34
 */
@Service
@Transactional
public class CarBrandServiceImpl implements CarBrandService {
    @Autowired
    private CarBrandDao carBrandDao;

    @Override
    public Integer insertCarBrand(CarBrand carBrand) {
        return carBrandDao.insertCarBrand(carBrand);
    }

    @Override
    public Integer deleteCarBrand(Integer brandId) {
        return carBrandDao.deleteCarBrand(brandId);
    }

    @Override
    public Integer updateCarBrand(CarBrand carBrand) {
        return carBrandDao.updateCarBrand(carBrand);
    }

    @Override
    public CarBrand getCarBrandById(Integer brandId) {
        return carBrandDao.getCarBrandById(brandId);
    }

    @Override
    public PageInfo<CarBrand> listCarBrand(CarBrand carBrand, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CarBrand> carBrandList = carBrandDao.listCarBrand(carBrand);
        PageInfo<CarBrand> pageInfo = new PageInfo<>(carBrandList);
        return pageInfo;
    }

    @Override
    public List<CarBrand> allCarBrand() {
        return carBrandDao.allCarBrand();
    }

    @Override
    public Integer countCarByBrandId(Integer brandId) {
        return carBrandDao.countCarByBrandId(brandId);
    }

    @Override
    public String getBrandIconByBrandId(Integer brandId) {
        return carBrandDao.getBrandIconByBrandId(brandId);
    }
}
