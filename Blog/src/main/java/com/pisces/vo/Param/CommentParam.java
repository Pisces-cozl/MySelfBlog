package com.pisces.vo.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentParam {
    private Integer articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
