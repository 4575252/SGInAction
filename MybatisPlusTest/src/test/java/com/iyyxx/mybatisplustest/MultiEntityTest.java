package com.iyyxx.mybatisplustest;

import com.iyyxx.mybatisplustest.domain.Order;
import com.iyyxx.mybatisplustest.domain.OrderDetail;
import com.iyyxx.mybatisplustest.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @className: MultiEntityTest
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/24/0024 15:11:41
 **/
@SpringBootTest
public class MultiEntityTest {

    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    void testInsert() {
        Order order = new Order();
        order.setDesc("订单1号");
        orderService.save(order);

        List<OrderDetail> orderDetails = Arrays.asList(
                new OrderDetail(null, order.getId(), "明细1"),
                new OrderDetail(null, order.getId(), "明细2")
        );
        System.out.println(orderDetails);
        System.out.println(1/0);
    }
}
