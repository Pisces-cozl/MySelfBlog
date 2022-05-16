package com.pisces.pojo.dos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Archives {

    private  Integer years;

    private Integer months;

    private Long counts;
}
