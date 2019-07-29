package com.example.dao;

import com.example.bean.entity.Car;
import com.example.bean.dto.CarPaging;
import com.example.dao.provider.CarDynaSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 车辆dao层
 *
 * @author HaN
 * @create 2019-03-28 22:15
 */
@Mapper
public interface CarDao {
    /**
     * 添加车辆
     *
     * @param car
     * @return
     */
    @Insert("insert into" +
            "      i_car(car_license,car_vin,model_id," +
            "      engine_code,com_id,car_location," +
            "      car_jointime,maintain_time,car_insurance," +
            "      car_price,car_color,car_seats," +
            "      ccp_type,ccp_card)" +
            "      values" +
            "      (#{carLicense},#{carVin},#{carModel.modelId}," +
            "      #{engineCode},#{company.comId},#{carLocation}," +
            "      #{carJointime},#{maintainTime},#{carInsurance}," +
            "      #{carPrice},#{carColor},#{carSeats}," +
            "      #{ccpType},#{ccpCard})")
    @Options(useGeneratedKeys = true, keyProperty = "carId")
    Integer insertCar(Car car);

    /**
     * 批量更改车辆运营状态
     * @param state 状态
     * @param carIds 车辆id集合
     * @return
     */
    Integer updateOperationState(@Param("state") int state,@Param("carIds") List<Integer> carIds);

    /**
     * 根据车辆id查找车辆
     *
     * @param carId
     * @return
     */
    @Select("select * from i_car where car_id = #{carId}")
    @Results({
            @Result(id = true, column = "car_id", property = "carId"),
            @Result(column = "model_id", property = "carModel",
                    one = @One(select = "com.example.dao.CarModelDao.getCarModelById", fetchType = FetchType.EAGER)),
            @Result(column = "com_id", property = "company",
                    one = @One(select = "com.example.dao.CompanyDao.getCompanyById", fetchType = FetchType.EAGER))
    })
    Car getCarById(Integer carId);

    /**
     * 动态修改车辆
     *
     * @param car
     * @return
     */
    @UpdateProvider(type = CarDynaSqlProvider.class, method = "updateCar")
    Integer updateCar(Car car);

    /**
     * 根据企业id查询该企业下所有车辆
     * @param comId
     * @return
     */
    @Select("SELECT * FROM \"i_car\" WHERE com_id = #{comId}")
    @Results({
            @Result(column = "model_id", property = "carModel",
                    one = @One(select = "com.example.dao.CarModelDao.getCarModelById", fetchType = FetchType.EAGER)),
            @Result(column = "com_id", property = "company",
                    one = @One(select = "com.example.dao.CompanyDao.getCompanyById", fetchType = FetchType.EAGER))
    })
    List<Car> listCarByComId(Integer comId);



    /**
     * 动态分页查询车辆列表
     *
     * @param carPaging
     * @return
     */
    @SelectProvider(type = CarDynaSqlProvider.class, method = "listCar")
    @Results({
            @Result(column = "model_id", property = "carModel",
                    one = @One(select = "com.example.dao.CarModelDao.getCarModelById", fetchType = FetchType.EAGER)),
            @Result(column = "com_id", property = "company",
                    one = @One(select = "com.example.dao.CompanyDao.getCompanyById", fetchType = FetchType.EAGER))
    })
    List<Car> listCar(CarPaging carPaging);

    /**
     *判断某车辆是否被租用(未被租用返回0)
     * @param carId
     * @return
     */
    @Select("select \"count\"(*)\n" +
            "from r_order_car oc INNER JOIN r_rental_order ro\n" +
            "on oc.order_id = ro.order_id \n" +
            "\tand oc.car_id=#{carId} and ro.state_id=4")
    Integer judgingCarWhetherToRentOrNot(Integer carId);


    /**
     * 通过车辆id集合批量查询车辆（用于批量违章，故不需要级联查询company和carModel）
     * @param carIds
     * @return
     */
    List<Car> listCarByIds(@Param("carIds") List<Integer> carIds);
}
