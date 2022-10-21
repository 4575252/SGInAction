package com.iyyxx;

import com.iyyxx.dao.StudentDao;
import com.iyyxx.domain.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @className: Demo
 * @description: 测试容器使用xml方式开发
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 10:44:33
 **/
public class Demo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        StudentDao dao = (StudentDao) applicationContext.getBean("studentDao");
        Student student = dao.getStudengById(30);
        System.out.println(student);
    }
}
