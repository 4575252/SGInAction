package com.iyyxx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iyyxx.domain.User;
import com.iyyxx.dao.UserDao;
import com.iyyxx.domain.PageResult;
import com.iyyxx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @className: UserServiceImpl
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/19/0019 17:42:32
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User findById(Integer id) {
        return userDao.findById(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    public void insertUser(User user) {
        userDao.insertUser(user);
        System.out.println(1/0);
        userDao.insertUser(user);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }

    public PageResult findByPage(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userDao.findAll();
        PageInfo pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult(pageInfo.getPageNum(),pageInfo.getPageSize(), (int) pageInfo.getTotal(),list);
        return pageResult;
    }
}
