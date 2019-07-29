package com.example.dao;

import com.example.bean.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author HaN
 * @create 2019-04-29 10:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional//单元测试结束之后会自动回滚
public class UserDaoTest {
    @Resource
    private UserDao userDao;

    @Test
    public void testGetUnitAndTypeById() {
        Integer userId = 1;
        User user = userDao.getUserById(userId);
        System.out.println(user);
//        List<Map<String, Object>> listMap = userDao.getUnitAndTypeById(userId);
//        System.out.println(listMap);

    }
}
