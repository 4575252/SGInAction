package com.iyyxx.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: InvokeLog
 * @description: 自定义注解开发
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 14:26:35
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface InvokeLog {
}
