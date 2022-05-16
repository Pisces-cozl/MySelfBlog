package com.pisces.controller;


import com.pisces.service.MsSysUserService;
import com.pisces.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@RestController
@RequestMapping("/users")
public class MsSysUserController {
    @Autowired
    private MsSysUserService msSysUserService;

    /**
     * 获取头部信息
     * @param token
     * @return
     */
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return     msSysUserService.findUserByToken(token);
    }
}

