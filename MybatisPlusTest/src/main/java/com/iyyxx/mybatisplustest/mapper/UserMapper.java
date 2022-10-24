package com.iyyxx.mybatisplustest.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.iyyxx.mybatisplustest.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @className: UserMapper
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/24/0024 9:23:41
 **/
public interface UserMapper extends BaseMapper<User> {
    User findMyUser(Long id);
    User findMyUserByWrapper(@Param(Constants.WRAPPER) Wrapper<User> wrapper);
}
