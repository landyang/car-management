package com.example.bean.dto;

import java.util.List;

/**
 * 树状结构基本接口
 *
 * @author HaN
 * @create 2019-05-26 14:22
 */
public interface DataTree<T> {
    public String getId();

    public String getParentId();

    public void setChildren(List<T> children);

    public List<T> getChildren();
}
