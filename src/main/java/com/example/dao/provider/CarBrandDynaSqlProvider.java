package com.example.dao.provider;

import com.example.bean.entity.CarBrand;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author HaN
 * @create 2019-04-02 21:55
 */
public class CarBrandDynaSqlProvider {
    /**
     * 动态插入车辆品牌
     * @param carBrand
     * @return
     */
    public String insertCarBrand(CarBrand carBrand) {
        return new SQL() {
            {
                INSERT_INTO("i_car_brand");
                if (carBrand.getFullname() != null && !"".equals(carBrand.getFullname())) {
                    VALUES("fullname", "#{fullname}");
                }
                if (carBrand.getSimplename() != null && !"".equals(carBrand.getSimplename())) {
                    VALUES("simplename", "#{simplename}");
                }
                if (carBrand.getBrandIcon() != null && !"".equals(carBrand.getBrandIcon())) {
                    VALUES("brand_icon", "#{brandIcon}");
                }
                if (carBrand.getSynopsis() != null && !"".equals(carBrand.getSynopsis())) {
                    VALUES("synopsis", "#{synopsis}");
                }
            }
        }.toString();
    }

    /**
     * 动态更新车辆品牌
     * @param carBrand
     * @return
     */
    public String updateCarBrand(CarBrand carBrand) {
        return new SQL() {
            {
                UPDATE("i_car_brand");
                if (carBrand.getFullname() != null && !"".equals(carBrand.getFullname())) {
                    SET("fullname = #{fullname}");
                }
                if (carBrand.getSimplename() != null && !"".equals(carBrand.getSimplename())) {
                    SET("simplename = #{simplename}");
                }
                if (carBrand.getBrandIcon() != null && !"".equals(carBrand.getBrandIcon())) {
                    SET("brand_icon = #{brandIcon}");
                }
                if (carBrand.getSynopsis() != null && !"".equals(carBrand.getSynopsis())) {
                    SET("synopsis = #{synopsis}");
                }
                WHERE("brand_id = #{brandId}");
            }
        }.toString();
    }

    /**
     * 分页动态查询
     * @param carBrand
     * @return
     */
    public String listCarBrand(CarBrand carBrand) {
        return new SQL() {
            {
                SELECT("*");
                FROM("i_car_brand");
                if (carBrand.getFullname() != null && !"".equals(carBrand.getFullname())) {
                    WHERE("fullname LIKE CONCAT ('%',#{fullname},'%')");
                }
                if (carBrand.getSimplename() != null && !"".equals(carBrand.getSimplename())) {
                    WHERE("simplename LIKE CONCAT ('%',#{simplename},'%')");
                }
            }
        }.toString();
    }
}
