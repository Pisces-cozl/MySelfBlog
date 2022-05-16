package com.pisces.service.impl;

import com.alibaba.fastjson.JSON;
import com.pisces.pojo.MsSysLog;
import com.pisces.mapper.MsSysLogMapper;
import com.pisces.pojo.MsSysUser;
import com.pisces.service.MsSysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pisces.service.MsSysUserService;
import com.pisces.util.JwtUtil;
import com.pisces.util.RescultEnum;
import com.pisces.util.Result;
import com.pisces.vo.SysLoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;


import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@Service
public class MsSysLogServiceImpl extends ServiceImpl<MsSysLogMapper, MsSysLog> implements MsSysLogService {
    @Autowired
    private MsSysUserService msSysUserService;

    @Autowired
    private RedisTemplate<String, String> redis;

    private static final String salt="mszlu!@#";

    /**
     * @param sysLoginVo
     * @return
     */
    @Override
    public Result login(SysLoginVo sysLoginVo) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码查询是否存在，
         * 3.不存在 登录失败，
         * 4.存在进行JWT生成token 返回前端
         * 5.token放入redis当中进行存储  并设置过期时间 (登录认证先认证token是否合法，在进redis中认证是否存在)
         */
        String account = sysLoginVo.getAccount();
        String password = sysLoginVo.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){

            return  Result.fail(RescultEnum. PARAMS_ERROR.getCode(),RescultEnum. PARAMS_ERROR.getMsg());
        }
        //对密码进行 md5加密 注意因为md5会被彩虹表破解所以再上一个salt
         password= DigestUtils.md5Hex(password+salt);
        MsSysUser msSysUser = msSysUserService.findUser(account,password);
        if (msSysUser==null){
            return  Result.fail(RescultEnum. ACCOUNT_PWD_NOT_EXIST.getCode(),RescultEnum. ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        String token = JwtUtil.createToken(msSysUser.getId());
        //把生成的token放入redis 并设置过期时间 1天
        redis.opsForValue().set("token_"+token,JSON.toJSONString(msSysUser),1,TimeUnit.DAYS);

        return Result.success(token);
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    @Override
    public MsSysUser checkToken(String token) {
//        检验是否合法
        if (StringUtils.isBlank(token)){
            return null;
        }
//        检验是否为空
        Map<String, Object> map = JwtUtil.checkToken(token);
        if (map == null){
            return  null;
        }
//        检验是否存在
        String redisToken= redis.opsForValue().get("token_" + token);
        if (StringUtils.isBlank(redisToken)){
            return  null;
        }
        MsSysUser msSysUser = JSON.parseObject(redisToken, MsSysUser.class);
        return  msSysUser;
    }

    /**
     * 用户登出
     * @param token
     * @return
     */
    @Override
    public Result loginOut(String token) {
        redis.delete("token_"+token);
        return Result.success(null);
    }

    /**
     * 注册功能
     * @param sysLoginVo
     * @return
     */
    @Override
    public Result register(SysLoginVo sysLoginVo) {
        /**
         * 1.判断参数是否合法
         * 2.判断账户是否存在
         *  2.1存在返回被注册
         *  2.2不存在进行注册用户
         * 3.生成token 并存入redis
         * 4. 加上事务，防止用户注册中途出现问题，注册的用户需要回滚
         */
        String account = sysLoginVo.getAccount();
        String password = sysLoginVo.getPassword();
        String nickname = sysLoginVo.getNickname();

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)){
            return  Result.fail(RescultEnum.PARAMS_ERROR.getCode(),RescultEnum.PARAMS_ERROR.getMsg());
        }
         MsSysUser  msSysUser = msSysUserService.findUserByAccount(account);

        if (msSysUser != null){
            return Result.fail(RescultEnum.ACCOUNT_EXIST.getCode(),RescultEnum.ACCOUNT_EXIST.getMsg());
        }

        MsSysUser SysUser=new MsSysUser();
        //进行赋值
        SysUser.setAccount(account);
        SysUser.setNickname(nickname);


        SysUser.setPassword(password);
        //进行注册事需要给密码进行一个加密
        SysUser.setPassword(DigestUtils.md5Hex(password+salt));
        SysUser.setCreateDate(System.currentTimeMillis());
        SysUser.setLastLogin(System.currentTimeMillis());
        SysUser.setAvatar("https://picsum.photos/id/1004/200/300");
        SysUser.setAdmin(true); //1 为true
        SysUser.setDeleted(false); // 0 为false
        SysUser.setSalt("");
        SysUser.setStatus("");
        SysUser.setEmail("");
        this.msSysUserService.saves(SysUser);

        //把生成的token放入redis 并设置过期时间 1天
        String token = JwtUtil.createToken(SysUser.getId());
        redis.opsForValue().set("token_"+token,JSON.toJSONString(SysUser),1,TimeUnit.DAYS);
        return Result.success(token);
    }
}
