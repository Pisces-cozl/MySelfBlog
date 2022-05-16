package com.pisces.vo.Param;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 页码
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageParams {
    private int page=1;
    private int pageSize=10;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long tagId;

    private String year;

    private String month;

    public String getMonth(){
        if (this.month !=null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}
