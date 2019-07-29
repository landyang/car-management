package com.example.dao;

import com.example.bean.entity.CarBrand;
import com.example.dao.provider.CarBrandDynaSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 车辆品牌dao层
 *
 * @author HaN
 * @create 2019-03-30 17:12
 */
@Mapper
public interface CarBrandDao {
    /**
     * 动态添加车辆品牌
     *
     * @param carBrand
     * @return
     */
    @InsertProvider(type = CarBrandDynaSqlProvider.class, method = "insertCarBrand")
    Integer insertCarBrand(CarBrand carBrand);

    /**
     * 根据车辆品牌id删除车辆品牌
     *
     * @param brandId
     * @return
     */
    @Delete("delete from i_car_brand where brand_id = #{brandId}")
    Integer deleteCarBrand(Integer brandId);

    /**
     * 动态修改车辆品牌
     *
     * @param carBrand
     * @return
     */
    @UpdateProvider(type = CarBrandDynaSqlProvider.class, method = "updateCarBrand")
    Integer updateCarBrand(CarBrand carBrand);

    /**
     * 根据品牌id查询车辆品牌详情
     *
     * @param brandId
     * @return
     */
    @Select("select * from i_car_brand where brand_id = #{brandId}")
    @Results({
            @Result(id = true, column = "brand_id", property = "brandId"),
            @Result(column = "brand_id", property = "carModels",
                    many = @Many(select = "com.example.dao.CarModelDao.getCarModelByBrandId", fetchType = FetchType.EAGER))
    })
    CarBrand getCarBrandById(Integer brandId);

    /**
     * 查询品牌信息不包括品牌下的所有车型
     *
     * @param brandId
     * @return
     */
    @Select("select * from i_car_brand where brand_id = #{brandId}")
    @Results({
            @Result(id = true, column = "brand_id", property = "brandId")
    })
    CarBrand getCarBrandNoModels(Integer brandId);

    /**
     * 动态查询车辆品牌列表
     *
     * @param carBrand
     * @return
     */
    @SelectProvider(type = CarBrandDynaSqlProvider.class, method = "listCarBrand")
    @Results({
            @Result(id = true, column = "brand_id", property = "brandId")
    })
    List<CarBrand> listCarBrand(CarBrand carBrand);

    /**
     * 查询全部车辆品牌及其型号
     *
     * @return
     */
    @Select("select * from i_car_brand")
    @Results({
            @Result(id = true, column = "brand_id", property = "brandId"),
            @Result(column = "brand_id", property = "carModels",
                    many = @Many(select = "com.example.dao.CarModelDao.getCarModelByBrandId", fetchType = FetchType.EAGER))
    })
    List<CarBrand> allCarBrand();

    /**
     * 统计某车辆品牌的车辆数
     * @param brandId
     * @return
     */
//    @Select("select count(*) from i_car " +
//            "where model_id in(" +
//            "select model_id from i_car_model " +
//            "where brand_id = #{brandId})")
    @Select("select count(*) " +
            "from i_car inner join i_car_model " +
            "on i_car.model_id = i_car_model.model_id " +
            "and brand_id = #{brandId}")
    Integer countCarByBrandId(Integer brandId);

    /**
     * 查找车辆品牌图标
     *
     * @param brandId
     * @return
     */
    @Select("select brand_icon from i_car_brand where brand_id = #{brandId}")
    String getBrandIconByBrandId(Integer brandId);
}
