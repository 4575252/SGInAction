package com.iyyxx.dao;


import com.iyyxx.domain.Order;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @className: UserDao
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/19/0019 17:43:05
 **/
public interface OrderDao {

    @Select("select * from orders")
    List<Order> findAll();

    Order findOrderAndUser(int id);
}
