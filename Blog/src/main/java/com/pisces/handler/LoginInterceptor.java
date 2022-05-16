package com.pisces.handler;


import com.alibaba.fastjson.JSON;
import com.pisces.pojo.MsSysUser;
import com.pisces.service.MsSysLogService;
import com.pisces.util.RescultEnum;
import com.pisces.util.Result;
import com.pisces.util.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  登录拦截器
 */
@Component
@Slf4j
public class LoginInterceptor  implements HandlerInterceptor {

    @Autowired
    private MsSysLogService msSysLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller  方法之前执行 此方法
        /**
         * 1.需要判断请求的接口路径是否为handlerMethod(controller方法)
         * 2.判断token是否为空  如果为空 未登录
         * 3.token不为空进行登录验证 loginService checkToken
         * 4. 认证成功就放行
         */

        //判断是否为HandlerMethod方法 不是就进行放行
        if (!(handler instanceof HandlerMethod)){
            return  true;
        }
        //获取token
        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (StringUtils.isBlank(token)){
            Result fail = Result.fail(RescultEnum.NO_LOGIN.getCode(), RescultEnum.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(JSON.toJSONString(fail));
            return  false;
        }
        MsSysUser msSysUser = msSysLogService.checkToken(token);
        if (msSysUser == null){
            Result fail = Result.fail(RescultEnum.NO_LOGIN.getCode(), RescultEnum.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(JSON.toJSONString(fail));
            return  false;
        }

        UserThreadLocal.put(msSysUser);
        //登录成功进行放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            //用完线层要删除 如果不删除有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
