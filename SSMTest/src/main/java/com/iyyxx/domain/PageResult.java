package com.iyyxx.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: PageResult
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 9:24:17
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult <T>{
    private Integer currentPage;
    private Integer pageSize;
    private Integer total;
    private List<T> data;
}
