package com.example.bean.dto;

import lombok.Data;

import java.util.List;

/**
 * 车辆地图树形列表
 * @author HaN
 * @create 2019-04-30 19:03
 */
public class MapMenuTree implements DataTree<MapMenuTree> {
    //相关id
    private String id;
    //父节点id
    private String parentId;
    //名称
    private String name;
    //所有孩子及孩子的孩子租车数量
    private Integer count;
    //孩子
    private List<MapMenuTree> children;

    @Override
    public String toString() {
        return "MapMenuTree{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", children=" + children +
                '}';
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public List<MapMenuTree> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<MapMenuTree> children) {
        this.children = children;
    }
}
