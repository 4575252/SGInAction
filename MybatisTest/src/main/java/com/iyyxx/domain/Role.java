package com.iyyxx.domain;

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
public class Role {
    private Integer id;
    private String name;
    private String desc;
}
