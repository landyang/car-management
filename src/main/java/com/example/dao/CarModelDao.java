package com.example.dao;

import com.example.bean.entity.CarBrand;
import com.example.bean.entity.CarModel;
import com.example.dao.provider.CarModelDynaSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 车辆型号dao层
 *
 * @author HaN
 * @create 2019-03-30 17:12
 */
@Mapper
public interface CarModelDao {

    /**
     * 动态增加车辆型号
     *
     * @param carModel
     * @return
     */
    @InsertProvider(type = CarModelDynaSqlProvider.class, method = "insertCarModel")
    Integer insertCarModel(CarModel carModel);

    /**
     * 根据车辆型号id删除车辆型号
     *
     * @param modelId
     * @return
     */
    @Delete("delete from i_car_model where model_id = #{modelId}")
    Integer deleteCarModel(Integer modelId);

    /**
     * 动态修改车辆型号
     *
     * @param carModel
     * @return
     */
    @UpdateProvider(type = CarModelDynaSqlProvider.class, method = "updateCarModel")
    Integer updateCarModel(CarModel carModel);

    /**
     * 根据车型id查询车型
     *
     * @param modelId
     * @return
     */
    @Select("select * from i_car_model where model_id = #{modelId}")
    @Results({
            @Result(id = true, column = "model_id", property = "modelId"),
            @Result(column = "brand_id", property = "carBrand",
                    one = @One(select = "com.example.dao.CarBrandDao.getCarBrandNoModels", fetchType = FetchType.EAGER))
    })
    CarModel getCarModelById(Integer modelId);

    /**
     * 查询全部车辆品牌及其型号
     *
     * @return
     */
    @Select("select * from i_car_model")
    @Results({
            @Result(id = true, column = "model_id", property = "modelId"),
            @Result(column = "brand_id", property = "carBrand",
                    one = @One(select = "com.example.dao.CarBrandDao.getCarBrandNoModels", fetchType = FetchType.EAGER))
    })
    List<CarModel> allCarModel();

    /**
     * 动态查询车辆型号列表
     *
     * @param carModel
     * @return
     */
    @SelectProvider(type = CarModelDynaSqlProvider.class, method = "listCarModel")
    @Results({
            @Result(id = true, column = "model_id", property = "modelId"),
            @Result(column = "brand_id", property = "carBrand",
                    one = @One(select = "com.example.dao.CarBrandDao.getCarBrandNoModels", fetchType = FetchType.EAGER))
    })
    List<CarModel> listCarModel(CarModel carModel);

    /**
     * 根据车辆品牌id查询车型
     *
     * @param brandId
     * @return
     */
    @Select("select model_id,model,model_image " +
            "from i_car_model " +
            "where brand_id = #{brandId}")
    @Results({
            @Result(id = true, column = "model_id", property = "modelId"),
            @Result(column = "model", property = "model"),
            @Result(column = "model_image", property = "modelImage")
    })
    List<CarModel> getCarModelByBrandId(Integer brandId);

    /**
     * 统计某车辆型号的车辆数
     * @param modelId
     * @return
     */
    @Select("select count(*) from i_car where model_id = #{modelId}")
    Integer countCarByModelId(Integer modelId);

    /**
     * 查找车辆型号图片
     *
     * @param modelId
     * @return
     */
    @Select("select model_image from i_car_model where model_id = #{modelId}")
    String getModelImageByModelId(Integer modelId);
}
