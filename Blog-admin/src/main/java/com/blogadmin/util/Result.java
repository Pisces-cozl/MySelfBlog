package com.blogadmin.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {


    /**
     * 是否成功
     */
    private  boolean success;

    /**
     * 状态码
     */
    private  int code;

    /**
     * 返回的信息
     */
    private String message;

    /**
     *  返回的数据
     */
    private Object data;


    /**
     * 成功返回
     * @param data
     * @return
     */
    public static Result success(Object data){
        return  new Result(true,200,"success",data);
    }

    public  static  Result fail(int code ,String message){
        return  new Result(false,code,message,null);
    }
}
