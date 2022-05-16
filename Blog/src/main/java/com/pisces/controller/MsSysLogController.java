package com.pisces.controller;


import com.pisces.service.MsSysLogService;
import com.pisces.util.Result;
import com.pisces.vo.SysLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@RestController
@RequestMapping("/login")
public class MsSysLogController {
    @Autowired
    private MsSysLogService msSysLogService;

    /**
     * 用户登录
     * @return
     */
    @PostMapping
    public Result Login(@RequestBody SysLoginVo sysLoginVo){
        return msSysLogService.login(sysLoginVo);
    }
}

