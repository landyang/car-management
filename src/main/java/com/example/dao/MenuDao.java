package com.example.dao;

import com.example.bean.dto.MapMenuTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *树形菜单dao层
 *
 * @author HaN
 * @create 2019-05-26 12:09
 */
@Mapper
public interface MenuDao {
    /**
     * 地图模式 省市县树形菜单查询（MapMenuTree格式）：
     */
    @Select("SELECT\n" +
            "    dr.\"id\",dr.\"name\",dr.parent_id,count(ro.car_id)\n" +
            "    FROM\n" +
            "    d_region AS dr\n" +
            "    LEFT JOIN\n" +
            "    i_unit AS iu  on\n" +
            "    iu.unit_areacode = dr.\"id\"\n" +
            "    LEFT JOIN\n" +
            "    r_rental_order AS rr on\n" +
            "    rr.unit_id = iu.unit_id\n" +
            "    and rr.state_id = 4\n" +
            "    LEFT JOIN\n" +
            "    r_order_car AS ro on\n" +
            "    ro.order_id = rr.order_id\n" +
            "    GROUP BY\n" +
            "    dr.\"id\",dr.\"name\",dr.parent_id\n" +
            "    order by dr.\"id\" asc")
    List<MapMenuTree> getMapMenuList();


    /**
     * 地图模式 全部单位租车状况查询（MapMenuTree格式）：
     * @return
     */
    @Select("select\n" +
            "iu.unit_id as id,iu.unit_name as name,iu.unit_areacode as parent_id,count(roc.car_id)\n" +
            "from\n" +
            "i_unit as iu \n" +
            "LEFT JOIN r_rental_order as rro\n" +
            "on rro.unit_id = iu.unit_id and rro.state_id = 4\n" +
            "LEFT JOIN r_order_car as roc\n" +
            "on roc.order_id = rro.order_id\n" +
            "GROUP BY iu.unit_id,iu.unit_name,iu.unit_areacode\n" +
            "ORDER BY iu.unit_id asc")
    List<MapMenuTree> getUnitsMenuList();

    /**
     * 地图模式 根据地区查询该地区全部单位租车状况（MapMenuTree格式）：
     * @return
     */
    @Select("select\n" +
            "iu.unit_id as id,iu.unit_name as name,iu.unit_areacode as parent_id,count(roc.car_id)\n" +
            "from\n" +
            "i_unit as iu\n" +
            "LEFT JOIN r_rental_order as rro\n" +
            "on rro.unit_id = iu.unit_id and rro.state_id = 4\n" +
            "LEFT JOIN r_order_car as roc\n" +
            "on roc.order_id = rro.order_id\n" +
            "where iu.unit_areacode = #{unitAreacode}\n" +
            "GROUP BY iu.unit_id,iu.unit_name,iu.unit_areacode\n" +
            "ORDER BY iu.unit_id asc")
    List<MapMenuTree> getUnitMenuListByAreacode(String unitAreacode);
}
