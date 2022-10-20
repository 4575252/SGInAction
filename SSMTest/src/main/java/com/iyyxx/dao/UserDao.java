package com.iyyxx.dao;

import com.iyyxx.controller.User;

import java.util.List;

/**
 * @className: UserDao
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/19/0019 17:43:05
 **/
public interface UserDao {

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User findById(Integer id);

    List<User> findAll();

    void insertUser(User user);

    void updateUser(User user);

    void deleteById(Integer id);
}
