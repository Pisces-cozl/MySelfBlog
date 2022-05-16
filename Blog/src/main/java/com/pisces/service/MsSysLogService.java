package com.pisces.service;

import com.pisces.pojo.MsSysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pisces.pojo.MsSysUser;
import com.pisces.util.Result;
import com.pisces.vo.SysLoginVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@Transactional//事务注解
public interface MsSysLogService extends IService<MsSysLog> {

    /**
     * 登录功能
     * @param sysLoginVo
     * @return
     */
    Result login(SysLoginVo sysLoginVo);

    /**
     * 校验token
     * @param token
     * @return
     */
    MsSysUser checkToken(String token);

    /**
     * 用户登出
     * @param token
     * @return
     */
    Result loginOut(String token);

    /**
     * 注册功能
     * @param sysLoginVo
     * @return
     */
    Result register(SysLoginVo sysLoginVo);
}
