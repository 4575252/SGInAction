package com.iyyxx.mybatisplustest.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @className: User
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/19/0019 17:36:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`order`")
public class Order {
//    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField(value = "`desc`")
    private String desc;
}
