package com.iyyxx.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @className: MyAspect
 * @description: aop切面类测试
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 10:04:29
 **/
@Aspect
@Component
public class MyAspect {
    // 定义切点
    @Pointcut("execution(* com.iyyxx.service..*.*(..))")
    public void pt(){

    }

    // 进行增强
    @Before("pt()")
    public void before(){
        System.out.println(">>>>>>>>>>>>>>> before");
    }
}
