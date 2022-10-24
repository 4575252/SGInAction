package com.iyyxx.mybatisplustest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyyxx.mybatisplustest.domain.User;
import com.iyyxx.mybatisplustest.mapper.UserMapper;
import com.iyyxx.mybatisplustest.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @className: UserServiceImpl
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/24/0024 12:07:11
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
