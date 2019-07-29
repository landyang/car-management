package com.example.service;

import com.example.bean.entity.User;

/**
 * 用户相关service接口
 *
 * @author HaN
 * @create 2019-04-29 10:57
 */
public interface UserService {
    User getUserById(Integer userId);
}
