package com.pisces.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
        //防止前端精度损失 把id转为String
//      @JsonSerialize(using = ToStringSerializer.class)
      private  String id;

      private UserVo author;

    /**
     * 子评论
     */
      private List<CommentVo> childrens;

    /**
     * 评论时间
     */
      private String createDate;

      private Integer level;

      private UserVo toUser;
}
