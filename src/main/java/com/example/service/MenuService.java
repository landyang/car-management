package com.example.service;

import com.example.bean.dto.MapMenuTree;

import java.util.List;

/**
 * 树形菜单service接口
 *
 * @author HaN
 * @create 2019-05-26 14:41
 */
public interface MenuService {
    List<MapMenuTree> getMapMenuList();
    List<MapMenuTree> getUnitMenuListByAreacode(String unitAreacode);
}
