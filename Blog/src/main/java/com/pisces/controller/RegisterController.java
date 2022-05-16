package com.pisces.controller;


import com.pisces.service.MsSysLogService;
import com.pisces.util.Result;
import com.pisces.vo.SysLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private MsSysLogService msSysLogService;

    @PostMapping
    public Result register(@RequestBody SysLoginVo sysLoginVo){
        return  msSysLogService.register(sysLoginVo);
    }
}
