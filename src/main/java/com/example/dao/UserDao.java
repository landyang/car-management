package com.example.dao;

import com.example.bean.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户dao层
 *
 * @author HaN
 * @create 2019-04-27 10:10
 */
@Mapper
public interface UserDao {
    @Select("select user_id,user_unit,user_type\n" +
            "from i_user\n" +
            "where user_id = #{userId};")
    User getUserById(Integer userId);
}
