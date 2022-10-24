package com.iyyxx.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @className: Order
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/19/0019 17:36:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private Date createtime;
    private Integer age;
    private String remark;
    private Integer userId;
    private User user;
}
