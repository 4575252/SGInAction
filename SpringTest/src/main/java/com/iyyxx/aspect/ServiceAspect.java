package com.iyyxx.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @className: ServiceAspect
 * @description: 切面测试类
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 13:49:02
 **/
@Component
@Aspect
public class ServiceAspect {

    /*
        表单式
            *   1、表示任意返回值，2、表示类，3、表示方法
            service后面有2个点，表示下一级包
            括号里有两个参数表示任意参数
     */
    @Pointcut("execution(* com.iyyxx.service..*.he*(..))")
    public void pointCut(){

    }

    @Before("pointCut()")
    public void methodBefore(JoinPoint joinPoint){
        System.out.println("class="+joinPoint.getSignature().getDeclaringType());
        System.out.println("method="+joinPoint.getSignature().getName());
        System.out.println("方法被调用了");
    }

    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void methodAfterThrowing(JoinPoint joinPoint, Throwable e){

        System.out.println("捕获到异常信息"+e.getMessage());
    }


    @Pointcut("@annotation(com.iyyxx.aspect.InvokeLog)")
    public void pointCut2(){

    }

    @Around("pointCut2()")
    public Object methodAround(ProceedingJoinPoint proceedingJoinPoint){

        System.out.println("around Before 目标方法执行前执行");
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed(); //目标方法执行
            System.out.println("around AfterRetuing 正常返回后通知");
        }catch (Throwable throwable){
            throwable.printStackTrace();
            System.out.println("around AfterThrowing 仅异常通知");
        }finally {
            System.out.println("around After 强制返回后通知，有异常也执行");
        }
        return result;
    }
}
