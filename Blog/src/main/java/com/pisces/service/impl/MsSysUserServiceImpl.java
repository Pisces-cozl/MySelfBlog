package com.pisces.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pisces.pojo.MsSysUser;
import com.pisces.mapper.MsSysUserMapper;
import com.pisces.service.MsSysLogService;
import com.pisces.service.MsSysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pisces.util.RescultEnum;
import com.pisces.util.Result;
import com.pisces.vo.Param.LoginVo;
import com.pisces.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@Service
public class MsSysUserServiceImpl extends ServiceImpl<MsSysUserMapper, MsSysUser> implements MsSysUserService {
    @Autowired
    private MsSysUserMapper msSysUserMapper;

    @Autowired
    private MsSysLogService msSysLogService;

    /**
     * 通过id查找用户
     * @param id
     * @return
     */
    @Override
    public MsSysUser findUserById(Long id) {
        MsSysUser msSysUser = msSysUserMapper.selectById(id);
        if (msSysUser==null){
            msSysUser.setNickname("码神之路");
        }
        return  msSysUser;
    }

    /**
     * 查找用户和密码
     * @param account
     * @param password
     * @return
     */
    @Override
    public MsSysUser findUser(String account, String password) {
        LambdaQueryWrapper<MsSysUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MsSysUser::getAccount,account);
        lambdaQueryWrapper.eq(MsSysUser::getPassword,password);
        lambdaQueryWrapper.select(MsSysUser::getAccount,MsSysUser::getId,MsSysUser::getAvatar,MsSysUser::getNickname);
        lambdaQueryWrapper.last("limit 1");

        return  msSysUserMapper.selectOne(lambdaQueryWrapper);
    }

    /**
     * 根据token 获取用户信息
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.token是否合法
         *      1.1 是否为空
         *      1.2解析是否为空
         *      1.3 redis是否存在
         * 2.检验是否返回错误
         * 3.检验成功返回对应的结果
         */
        MsSysUser msSysUser = msSysLogService.checkToken(token);
        if (msSysUser == null){
            return Result.fail(RescultEnum.TOKEN_ERROR.getCode(),RescultEnum.TOKEN_ERROR.getMsg());
        }
        //进行赋值 把获取到的用户信息放入vo当中
        LoginVo loginVo=new LoginVo();
        loginVo.setId(String.valueOf(msSysUser.getId()));
        loginVo.setAccount(msSysUser.getAccount());
        loginVo.setAvatar(msSysUser.getAvatar());
        loginVo.setNickName(msSysUser.getNickname());
        return Result.success(loginVo);
    }

    /**
     * 根据账户查找用户信息
     * @param account
     * @return
     */
    @Override
    public MsSysUser findUserByAccount(String account) {
        LambdaQueryWrapper<MsSysUser> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MsSysUser::getAccount,account);
        lambdaQueryWrapper.last("limit 1");
        return this.msSysUserMapper.selectOne(lambdaQueryWrapper);
    }

    /**
     * 保存用户
     * @param msSysUser
     */
    @Override
    public void saves(MsSysUser msSysUser) {
        // 保存用户
            this.msSysUserMapper.insert(msSysUser);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public UserVo findSysUserById(Long id) {
        MsSysUser msSysUser = msSysUserMapper.selectById(id);
        if (msSysUser==null){
            msSysUser.setNickname("码神之路");
        }
        UserVo userVo=new UserVo();
        //也可以写 BeanUtils.copyProperties(msSysUser,userVo)
        userVo.setId(String.valueOf(msSysUser.getId()));
        //头像来自这个网站 https://picsum.photos/images#1
        userVo.setAvatar("https://picsum.photos/id/1062/200/300");
        userVo.setNickname(msSysUser.getNickname());
        return  userVo;
    }
}
