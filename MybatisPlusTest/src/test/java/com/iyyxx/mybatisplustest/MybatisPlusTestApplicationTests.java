package com.iyyxx.mybatisplustest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyyxx.mybatisplustest.domain.User;
import com.iyyxx.mybatisplustest.mapper.UserMapper;
import com.iyyxx.mybatisplustest.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisPlusTestApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void testFindAllUser() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    void testWrapperQuery() {
        QueryWrapper<User> queryWrapper = new QueryWrapper(new User());
        queryWrapper.gt("age", 18);
        queryWrapper.orderByDesc("name");
//        queryWrapper.select("id","name");
        queryWrapper.select(tableFieldInfo->!tableFieldInfo.getColumn().equals("user_name"));
//        System.out.println(queryWrapper.getCustomSqlSegment());   //queryWrapper核心代码片段
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);

    }


    @Test
    void testLambdaQueryWrapper() {
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper(new User());
//        queryWrapper.gt(User::getAge, 18);
//        queryWrapper.select(tableFieldInfo->!tableFieldInfo.getColumn().equals("user_name"));
//        queryWrapper.orderByDesc(User::getName);
//        List<User> users = userMapper.selectList(queryWrapper);
//        System.out.println(users);

        List<User> users1 = userService.lambdaQuery()
                .gt(User::getAge, 18)
                .orderByDesc(User::getName)
                .list();
        System.out.println(users1);
    }

    @Test
    void testMybatisMethod() {
        User myUser = userMapper.findMyUser(2l);
        System.out.println(myUser);
    }

    @Test
    void testMybatisWrapper() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper(new User());
        queryWrapper.eq(User::getId, 2);
        queryWrapper.orderByDesc(User::getName);
        User myUser = userMapper.findMyUserByWrapper(queryWrapper);
        System.out.println(myUser);
    }

    @Test
    void testPage1() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper(new User());
        queryWrapper.gt(false,User::getAge, 1l);
        queryWrapper.orderByDesc(User::getName);

        IPage<User> page = new Page<>();
        page.setSize(1);
        page.setCurrent(2);

        userMapper.selectPage(page,queryWrapper);
        System.out.println(page);
    }

    @Test
    void testService() {
        System.out.println(userService.count());
    }


    @Test
    void testInsert() {
        User user = new User(null, "zhangsan", "123456", "张三", 15, "福建");
        userService.save(user);
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setId(9l);
        user.setVersion(5);
        user.setName("张三");
        user.setAddress("厦门");
        userService.saveOrUpdate(user);
//      userService.lambdaUpdate()
//                .eq(User::getName, user.getName())
//                .update(user);
//        List<User> users = userService.lambdaQuery().eq(User::getName, "张三")
//                .list();
//        userService.lambdaUpdate().
    }

    @Test
    void testLogicDel(){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName, "张三");
        userService.remove(wrapper);
    }
}
