package com.iyyxx.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: Student
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 10:45:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private int id;
    private int age;
}
