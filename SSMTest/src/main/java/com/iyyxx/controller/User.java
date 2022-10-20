package com.iyyxx.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: User
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/19/0019 17:36:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private Integer age;
    private String address;
}
