package com.iyyxx.controller;

import com.iyyxx.domain.PageResult;
import com.iyyxx.domain.ResponseResult;
import com.iyyxx.domain.User;
import com.iyyxx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: UserController
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/19/0019 17:36:15
 **/
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseResult findByID(@PathVariable Integer id){
        User user = userService.findById(id);
        if(user == null){
            return new ResponseResult(500, "没有该用户");
        }
        return new ResponseResult(200, "查询成功", user);
    }


    @GetMapping("/user")
    public ResponseResult findAll(){
        List<User> list = userService.findAll();
        if(list == null){
            return new ResponseResult(500, "没有数据");
        }
        return new ResponseResult(200, "查询成功", list);
    }


    @PostMapping("/user")
    public ResponseResult insertUser(@RequestBody User user){
        userService.insertUser(user);
        return new ResponseResult(200, "保存成功");
    }

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody User user){
        userService.updateUser(user);
        return new ResponseResult(200, "更新成功");
    }


    @DeleteMapping("/user/{id}")
    public ResponseResult delete(@PathVariable Integer id){
        userService.deleteById(id);
        return new ResponseResult(200, "删除成功");
    }


    @GetMapping("/user/{pageSize}/{pageNum}")
    public ResponseResult findByPage(@PathVariable(value = "pageSize") Integer pageSize, @PathVariable(value = "pageNum") Integer pageNum){
        PageResult pageResult = userService.findByPage(pageSize, pageNum);
        return new ResponseResult(200, "查询成功", pageResult);
    }
}
