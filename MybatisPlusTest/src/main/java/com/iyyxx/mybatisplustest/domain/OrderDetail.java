package com.iyyxx.mybatisplustest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: User
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/19/0019 17:36:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    private Long id;
    private Long oid;
    private String desc;
}
