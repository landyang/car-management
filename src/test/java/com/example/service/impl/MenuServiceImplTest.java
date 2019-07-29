package com.example.service.impl;

import com.example.bean.dto.MapMenuTree;
import com.example.dao.MenuDao;
import com.example.service.MenuService;
import com.example.utils.JsonUtil;
import com.example.utils.TreeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HaN
 * @create 2019-05-26 14:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional//单元测试结束之后会自动回滚
public class MenuServiceImplTest {

    @Resource
    private MenuService menuServiceImpl;
    @Resource
    private MenuDao menuDao;

    @Test
    public void getMapMenuList() {
        /**
         * 所有省市县区的数据
         */
        List<MapMenuTree> mapMenuList = menuDao.getMapMenuList();


        /**
         * 全部单位租车状况
         */
        List<MapMenuTree> unitMenuList = menuDao.getUnitsMenuList();
        for (MapMenuTree mapMenuTree : unitMenuList) {

            for (MapMenuTree menuTree : mapMenuList) {
                if(menuTree.getId().equals(mapMenuTree.getParentId())) {
                    List<MapMenuTree> children = menuTree.getChildren();
                    if (children ==null) {
                        children = new ArrayList<>();
                    }
                    children.add(mapMenuTree);
                    menuTree.setChildren(children);
                }
            }
        }


        /**
         * 最终输出的树形结构类结果数据
         */
        List<MapMenuTree> treeNodeResultList = new ArrayList<>();

        /**
         * 三级节点：省-市-县区。
         * 数据库操作获取全部省市县区的数据 及 相应管辖单位的租车数量
         */
        //1、获取全部顶层节点，即parentId=0的节点：河南省...
        List<MapMenuTree> levelFirst = TreeUtil.getChildrenByParentId("0", mapMenuList);
        //2、根据全部顶层节点获取对应的第二层节点，并放入顶层节点的children中
        List<MapMenuTree> levelSecond = new ArrayList<>();
        for (MapMenuTree mapMenuTree : levelFirst) {
            List<MapMenuTree> childrenByParentId = TreeUtil.getChildrenByParentId(mapMenuTree.getId(), mapMenuList);
            mapMenuTree.setChildren(childrenByParentId);
            levelSecond.addAll(childrenByParentId);
        }
        //3、根据全部第二层节点获取对应的第三层节点，并放入第二层节点的children中（第三层包含租车数量，第二层此时可以计算租车数量）
        for (MapMenuTree mapMenuTree : levelSecond) {
            //3.1、获取该节点mapMenuTree的孩子节点
            List<MapMenuTree> childrenByParentId = TreeUtil.getChildrenByParentId(mapMenuTree.getId(), mapMenuList);
            //3.2、计算该节点租车数量count的和
            int count = mapMenuTree.getCount();
            for (MapMenuTree menuTree : childrenByParentId) {
                count += menuTree.getCount();
            }
            //3.3、设置该节点的孩子children和租车数量count
            mapMenuTree.setChildren(childrenByParentId);
            mapMenuTree.setCount(count);
        }
        //4、统计第一层各节点的租车数量
        for (MapMenuTree mapMenuTree : levelFirst) {
            List<MapMenuTree> childrenByParentId = TreeUtil.getChildrenByParentId(mapMenuTree.getId(), mapMenuList);
            //4.2、计算该节点租车数量count的和
            int count = mapMenuTree.getCount();
            for (MapMenuTree menuTree : childrenByParentId) {
                count += menuTree.getCount();
            }
            //3.3、设置该节点的租车数量count
            mapMenuTree.setCount(count);
        }


        treeNodeResultList = levelFirst;


        System.out.println(treeNodeResultList);


        String s = JsonUtil.objectToJson(treeNodeResultList);
        System.out.println(s);





        /*//所有省市县区的数据
        List<MapMenuTree> mapMenuList = menuDao.getMapMenuList();
         //最终输出的树形结构类结果数据
        List<MapMenuTree> treeNodeResultList = new ArrayList<>();
        //1、获取全部顶层节点，即parentId=0的节点：河南省...
        List<MapMenuTree> levelFirst = TreeUtil.getChildrenByParentId("0", mapMenuList);
        treeNodeResultList = levelFirst;
        //2、循环所有在结果数据中的顶层节点，并将子级节点装载进顶层节点的 children 属性
        for (MapMenuTree mapMenuTree : treeNodeResultList) {
            mapMenuTree.setChildren(TreeUtil.foreachChildrenTreeNode(mapMenuTree.getId(),mapMenuList));
        }*/
    }
}