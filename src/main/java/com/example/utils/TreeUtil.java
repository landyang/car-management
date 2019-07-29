package com.example.utils;


import com.example.bean.dto.DataTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaN
 * @create 2019-05-23 14:27
 */
public class TreeUtil {


    /**
     * 获取某节点的孩子节点（单层孩子节点）
     * 可用于获取全部顶层节点
     * @param parentId
     * @param treeNodeList
     * @param <T>
     * @return
     */
    public static <T extends DataTree<T>> List<T> getChildrenByParentId(String parentId, List<T> treeNodeList) {
        List<T> treeNodeResultList = new ArrayList<>();
        /**
         * 将父id为parentId的结点写入结果数据
         */
        for (T treeNode : treeNodeList) {
            if (parentId.equals(treeNode.getParentId())) {
                treeNodeResultList.add(treeNode);
            }
        }
        return treeNodeResultList;
    }


    /**
     * 循环遍历出子节点
     * (已知顶层节点id，构造所有顶层节点的子节点树)
     * @param parentId
     * @param treeNodeList
     * @return
     */
    public static <T extends DataTree<T>> List<T> foreachChildrenTreeNode(String parentId, List<T> treeNodeList) {
        //所有子节点集合
        List<T> children = new ArrayList<>();
        //遍历找出第一层子节点
        for(T treeNode : treeNodeList) {
            if(treeNode.getParentId() != null && parentId.equals(treeNode.getParentId())) {
                children.add(treeNode);
            }
        }

        //结束条件
        if(children.size() == 0) {
            return null;
        }

        //递归找出子节点的子节点。。。
        for(T child : children) {
            List<T> childChildren = foreachChildrenTreeNode(child.getId(), treeNodeList);
            /*if(childChildren == null) {
                childChildren = new ArrayList<>(0);
            }*/
            child.setChildren(childChildren);
        }
        return children;
    }


    /*//获取顶层节点
    public static <T extends DataTree<T>> List<T> getTreeList(String topId, List<T> entityList){
        List<T> resultList = new ArrayList<>();//存储顶层的数据

        Map<Object, T> treeMap = new HashMap<>();
        T itemTree;

        for(int i=0;i<entityList.size()&&!entityList.isEmpty();i++) {
            itemTree = entityList.get(i);
            treeMap.put(itemTree.getId(),itemTree);//把所有的数据放到map当中，id为key
            if(topId.equals(itemTree.getParentId()) || itemTree.getParentId() == null) {//把顶层数据放到集合中
                resultList.add(itemTree);
            }
        }

        //循环数据，把数据放到上一级的childen属性中
        for(int i = 0; i< entityList.size()&&!entityList.isEmpty();i++) {
            itemTree = entityList.get(i);
            T data = treeMap.get(itemTree.getParentId());//在map集合中寻找父亲
            if(data != null) {//判断父亲有没有
                if(data.getChildren() == null) {
                    data.setChildren(new ArrayList<>());
                }
                data.getChildren().add(itemTree);//把子节点 放到父节点childList当中
                treeMap.put(itemTree.getParentId(), data);//把放好的数据放回map当中
            }
        }
        return resultList;
    }*/
}
