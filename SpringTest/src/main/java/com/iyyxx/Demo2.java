package com.iyyxx;

import com.alibaba.druid.pool.DruidDataSource;
import com.iyyxx.config.ApplicationConfig;
import com.iyyxx.dao.StudentDao;
import com.iyyxx.domain.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @className: Demo
 * @description: 测试容器使用注解方式开发
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 10:44:33
 **/
public class Demo2 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        StudentDao dao = (StudentDao) applicationContext.getBean("studentDaoImpl2");
        Student student = dao.getStudengById(30);
        System.out.println(student);


//        DruidDataSource dataSource = (DruidDataSource) applicationContext.getBean("dataSource");
        DruidDataSource dataSource = applicationContext.getBean(DruidDataSource.class);
        System.out.println(dataSource.getUrl());
    }
}
