package com.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaN
 * @create 2019-05-23 10:49
 */
public class TestTreeNode {
    /**
     * 部门实体类
     */
    public class Department {

        public Department(Integer id, String name, Integer parentId) {
            this.id = id;
            this.name = name;
            this.parentId = parentId;
        }

        private Integer id;
        private String name;

        /**
         * 上级部门 id
         */
        private Integer parentId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }
    }

    /**
     * 树形节点实体类
     */
    public class TreeNode {

        public TreeNode(Integer id, String name, Integer parentId, List<TreeNode> children) {
            this.id = id;
            this.name = name;
            this.parentId = parentId;
            this.children = children;
        }

        private Integer id;
        private String name;

        /**
         * 父级节点 id
         */
        private Integer parentId;

        /**
         * 子级节点
         */
        private List<TreeNode> children;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        public void setChildren(List<TreeNode> children) {
            this.children = children;
        }
    }

    /**
     * 循环遍历出子节点
     * @param parentId
     * @param treeNodeList
     * @return
     */
    public List<TreeNode> forEachChildrenTreeNode(int parentId, List<TreeNode> treeNodeList) {
        //所有子节点集合
        List<TreeNode> children = new ArrayList<>(0);
        //遍历找出第一层子节点
        for(TreeNode treeNode : treeNodeList) {
            if(treeNode.getParentId() != null && treeNode.getParentId().equals(parentId)) {
                children.add(treeNode);
            }
        }

        //结束条件
        if(children.size() == 0) {
            return null;
        }

        //递归找出子节点的子节点。。。
        for(TreeNode child : children) {
            List<TreeNode> childChildren = forEachChildrenTreeNode(child.getId(), treeNodeList);
            /*if(childChildren == null) {
                childChildren = new ArrayList<>(0);
            }*/
            child.setChildren(childChildren);
        }
        return children;
    }

    /**
     * 测试代码
     */
    @Test
    public void testApp() {

        List<Department> departmentList = new ArrayList<>(6);
        departmentList.add(new Department(1,"测试集团公司1", null));
        departmentList.add(new Department(2,"测试分公司1", 1));
        departmentList.add(new Department(3,"测试分公司2", 1));
        departmentList.add(new Department(4,"测试子部门1", 3));
        departmentList.add(new Department(5,"测试项目室1", 4));
        departmentList.add(new Department(6,"测试项目组1", 5));
        departmentList.add(new Department(7,"测试项目室2", 4));
        departmentList.add(new Department(8,"测试子部门2", 3));
        departmentList.add(new Department(9,"测试分公司3", 1));
        departmentList.add(new Department(10,"测试集团公司2", null));

        /**
         * 将部门数据转成树形节点
         */
        List<TreeNode> treeNodeList = new ArrayList<>(10);
        for(Department department : departmentList) {
            treeNodeList.add(new TreeNode(department.getId(), department.getName(), department.getParentId(), new ArrayList<>()));
        }

        /**
         * 最终输出的树形结构类结果数据
         */
        List<TreeNode> treeNodeResultList = new ArrayList<>(0);

        /**
         * 1、将 parentId 为空的顶层节点首先写入结果数据
         */
        for(TreeNode treeNode : treeNodeList) {
            if(treeNode.getParentId() == null) {
                treeNodeResultList.add(treeNode);
            }
        }

        /**
         * 2、循环所有在结果数据中的顶层节点，并将子级节点装载进顶层节点的 children 属性
         */
        for(TreeNode treeNode : treeNodeResultList) {
            treeNode.setChildren(forEachChildrenTreeNode(treeNode.getId(), treeNodeList));
        }

        /**
         * 将结果数据以 JSON 格式输出
         */
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(treeNodeResultList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
