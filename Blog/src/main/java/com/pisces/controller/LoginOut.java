package com.pisces.controller;

import com.pisces.service.MsSysLogService;
import com.pisces.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logout")
public class LoginOut {
    @Autowired
    private MsSysLogService msSysLogService;

    /**
     * 登出
     * @param token
     * @return
     */
    @GetMapping
    public Result login(@RequestHeader("Authorization") String token){
        return  msSysLogService.loginOut(token);
    }
}
