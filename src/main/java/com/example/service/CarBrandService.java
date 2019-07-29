package com.example.service;

import com.example.bean.entity.CarBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author HaN
 * @create 2019-03-30 17:33
 */
public interface CarBrandService {
    Integer insertCarBrand(CarBrand carBrand);

    Integer deleteCarBrand(Integer brandId);

    Integer updateCarBrand(CarBrand carBrand);

    CarBrand getCarBrandById(Integer brandId);

    PageInfo<CarBrand> listCarBrand(CarBrand carBrand, Integer pageNum, Integer pageSize);

    List<CarBrand> allCarBrand();

    Integer countCarByBrandId(Integer brandId);

    String getBrandIconByBrandId(Integer brandId);
}
