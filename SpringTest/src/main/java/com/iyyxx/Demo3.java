package com.iyyxx;

import com.iyyxx.config.ApplicationConfig;
import com.iyyxx.service.impl.TestService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @className: Demo
 * @description: 测试AOP功能
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 10:44:33
 **/
public class Demo3 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        TestService bean = applicationContext.getBean(TestService.class);
        bean.hello();
        bean.test();
    }
}
