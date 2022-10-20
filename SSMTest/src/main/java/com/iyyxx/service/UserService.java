package com.iyyxx.service;

import com.iyyxx.controller.User;
import com.iyyxx.domain.PageResult;

import java.util.List;

/**
 * @className: UserService
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/19/0019 17:40:39
 **/
public interface UserService {
    User findById(Integer id);

    List<User> findAll();

    void insertUser(User user);

    void updateUser(User user);

    void deleteById(Integer id);

    PageResult findByPage(Integer pageSize, Integer pageNum);
}
