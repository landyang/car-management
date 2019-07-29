package com.example.dao.provider;

import com.example.bean.entity.CarModel;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author HaN
 * @create 2019-04-10 21:29
 */
public class CarModelDynaSqlProvider {
    //动态插入车辆型号
    public String insertCarModel(CarModel carModel) {
        return new SQL() {
            {
                INSERT_INTO("i_car_model");
                if (carModel.getCarBrand() != null && carModel.getCarBrand().getBrandId() != null) {
                    VALUES("brand_id", "#{carBrand.brandId}");
                }
                if (carModel.getModel() != null && !"".equals(carModel.getModel())) {
                    VALUES("model", "#{model}");
                }
                if (carModel.getModelImage() != null && !"".equals(carModel.getModelImage())) {
                    VALUES("model_image", "#{modelImage}");
                }
            }
        }.toString();
    }

    //动态更新车辆型号
    public String updateCarModel(CarModel carModel) {
        return new SQL() {
            {
                UPDATE("i_car_model");
                if (carModel.getCarBrand() != null && carModel.getCarBrand().getBrandId() != null) {
                    SET("brand_id = #{carBrand.brandId}");
                }
                if (carModel.getModel() != null && !"".equals(carModel.getModel())) {
                    SET("model = #{model}");
                }
                if (carModel.getModelImage() != null && !"".equals(carModel.getModelImage())) {
                    SET("model_image = #{modelImage}");
                }
                WHERE("model_id = #{modelId}");
            }
        }.toString();
    }

    //分页动态查询
    public String listCarModel(CarModel carModel) {
        return new SQL() {
            {
                SELECT("*");
                FROM("i_car_model");
                if (carModel.getCarBrand() != null && carModel.getCarBrand().getBrandId() != null) {
                    WHERE("brand_id = #{carBrand.brandId}");
                }
                if (carModel.getModel() != null && !"".equals(carModel.getModel())) {
                    WHERE("model LIKE CONCAT ('%',#{model},'%')");
                }
            }
        }.toString();
    }
}
