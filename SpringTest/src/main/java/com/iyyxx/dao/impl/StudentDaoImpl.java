package com.iyyxx.dao.impl;

import com.iyyxx.dao.StudentDao;
import com.iyyxx.domain.Student;

/**
 * @className: StudentDaoImpl
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 10:44:14
 **/
public class StudentDaoImpl implements StudentDao {
    public Student getStudengById(int id) {
        return new Student("国服最强西施", 30 , 30);
    }
}
