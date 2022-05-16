package com.pisces.handler;

import com.pisces.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

//统一异常类
// 对加了@Controller 注解的方法进行拦截 AOP的实现
@ControllerAdvice
public class AllExceptionHandler {

    //返回 json数据
    @ResponseBody
    //进行异常处理，处理 Exception.class下的 异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception exception){
        exception.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
