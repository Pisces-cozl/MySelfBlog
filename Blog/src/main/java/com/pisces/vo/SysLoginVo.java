package com.pisces.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SysLoginVo {

    private String account;
    private String password;
    private String nickname;
}
