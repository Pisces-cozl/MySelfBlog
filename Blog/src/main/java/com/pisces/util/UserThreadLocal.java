package com.pisces.util;

import com.pisces.pojo.MsSysUser;

/**
 * 用户本地线层
 */
public class UserThreadLocal {

    private UserThreadLocal(){

    }

    //线层变量隔离
    private static  final ThreadLocal<MsSysUser> LOCAL =new ThreadLocal<>();


    public static  void  put(MsSysUser msSysUser){
        LOCAL.set(msSysUser);
    }
    public static MsSysUser getMsSysUser(){
         return  LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
