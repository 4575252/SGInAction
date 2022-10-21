package com.iyyxx.dao.impl;

import com.iyyxx.dao.StudentDao;
import com.iyyxx.domain.Student;
import org.springframework.stereotype.Repository;

/**
 * @className: StudentDaoImpl
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 10:44:14
 **/
@Repository
public class StudentDaoImpl2 implements StudentDao {
    public Student getStudengById(int id) {
        return new Student("国服最强西施", 30 , 30);
    }
}
