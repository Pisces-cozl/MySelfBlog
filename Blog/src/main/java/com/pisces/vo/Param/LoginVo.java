package com.pisces.vo.Param;

import com.alibaba.druid.filter.AutoLoad;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String account;

    private String nickName;

    /**
     * 头像
     */
    private String avatar;
}
