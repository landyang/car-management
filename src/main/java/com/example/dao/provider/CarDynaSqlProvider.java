package com.example.dao.provider;

import com.example.bean.entity.Car;
import com.example.bean.dto.CarPaging;
import org.apache.ibatis.jdbc.SQL;

/**
 * 车辆动态SQL提供类
 *
 * @author HaN
 * @create 2019-03-31 15:50
 */
public class CarDynaSqlProvider {
    /**
     * 动态修改车辆
     * @param car
     * @return
     */
    public String updateCar(Car car) {
        return new SQL() {
            {
                UPDATE("i_car");
                if (car.getCarLicense() != null && !"".equals(car.getCarLicense())) {
                    SET("car_license = #{carLicense}");
                }
                if (car.getCarVin() != null && !"".equals(car.getCarVin())) {
                    SET("car_vin = #{carVin}");
                }
                if (car.getCarModel() != null && car.getCarModel().getModelId() != null) {
                    SET("model_id = #{carModel.modelId}");
                }
                if (car.getEngineCode() != null && !"".equals(car.getEngineCode())) {
                    SET("engine_code = #{engineCode}");
                }
                if (car.getCompany() != null && car.getCompany().getComId() != null) {
                    SET("com_id = #{company.comId}");
                }
                if (car.getCarLocation() != null && !"".equals(car.getCarLocation())) {
                    SET("car_location = #{carLocation}");
                }
                if (car.getCarJointime() != null) {
                    SET("car_jointime = #{carJointime}");
                }
                if (car.getMaintainTime() != null) {
                    SET("maintain_time = #{maintainTime}");
                }
                if (car.getCarInsurance() != null && !"".equals(car.getCarInsurance())) {
                    SET("car_insurance = #{carInsurance}");
                }
                if (car.getCarPrice() != null) {
                    SET("car_price = #{carPrice}");
                }
                if (car.getCarColor() != null && !"".equals(car.getCarColor())) {
                    SET("car_color = #{carColor}");
                }
                if (car.getCarSeats() != null) {
                    SET("car_seats = #{carSeats}");
                }
                if (car.getCcpType() != null && !"".equals(car.getCcpType())) {
                    SET("ccp_type = #{ccpType}");
                }
                if (car.getCcpCard() != null && !"".equals(car.getCcpCard())) {
                    SET("ccp_card = #{ccpCard}");
                }
                if (car.getOperationState() != null) {
                    SET("operation_state = #{operationState}");
                }
                WHERE("car_id = #{carId}");
            }
        }.toString();
    }

    /**
     * 动态分页查询车辆列表
     * @param carPaging
     * @return
     */
    public String listCar(CarPaging carPaging) {
        return new SQL() {
            {
                SELECT("*");
                FROM("i_car");
                if (carPaging.getCarLicense() != null && !"".equals(carPaging.getCarLicense())) {
                    WHERE("car_license LIKE CONCAT ('%',#{carLicense},'%')");
                }
                if (carPaging.getCarModel() != null && carPaging.getCarModel().getModelId() != null) {
                    WHERE("model_id = #{carModel.modelId}");
                }
                if (carPaging.getCompany() != null && carPaging.getCompany().getComId() != null) {
                    WHERE("com_id = #{company.comId}");
                }
                if (carPaging.getCarLocation() != null && !"".equals(carPaging.getCarLocation())) {
                    WHERE("car_location LIKE CONCAT ('%',#{carLocation},'%')");
                }
                if (carPaging.getCcpType() != null && !"".equals(carPaging.getCcpType())) {
                    WHERE("ccp_type LIKE CONCAT ('%',#{ccpType},'%')");
                }
                if (carPaging.getCcpCard() != null && !"".equals(carPaging.getCcpCard())) {
                    WHERE("ccp_card LIKE CONCAT ('%',#{ccpCard},'%')");
                }
                if (carPaging.getOperationState() != null) {
                    WHERE("operation_state = #{operationState}");
                }
            }
        }.toString();
    }
}
