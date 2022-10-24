package com.iyyxx.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @className: User
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/19/0019 17:36:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String username;
    private Integer age;
    private String address;
    private List<Role> roles;
}
