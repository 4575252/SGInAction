package com.iyyxx.service.impl;

import com.iyyxx.aspect.InvokeLog;
import org.springframework.stereotype.Service;

/**
 * @className: TestService
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 13:48:01
 **/
@Service
public class TestService {
    public String hello(){
//        System.out.println(1/0);
        return "Hello Spring AOP";
    }

    @InvokeLog
    public void test(){
        System.out.println("test service method");
    }
}
