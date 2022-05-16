package com.pisces.service;

import com.pisces.pojo.MsSysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pisces.util.Result;
import com.pisces.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
public interface MsSysUserService extends IService<MsSysUser> {

    /**
     * 查找用户id
     * @param id
     * @return
     */
    MsSysUser findUserById(Long id);

    /**
     * 查找用户名和密码进行登录
     * @param account
     * @param password
     * @return
     */
    MsSysUser findUser(String account, String password);


    /**
     * 根据token查找对应的用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户信息
     * @param account
     * @return
     */
    MsSysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param msSysUser
     */
    void saves(MsSysUser msSysUser);

    /**
     * @param id
     * @return
     */
    UserVo findSysUserById(Long id);
}
