package com.iyyxx.handler;

import com.iyyxx.domain.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @className: GlobalExceptionHandler
 * @description: 全局异常捕获
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 9:46:18
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseResult allExceptionHandler(Exception ex){
        return new ResponseResult(500, ex.getMessage());
    }
}
