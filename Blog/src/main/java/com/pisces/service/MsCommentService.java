package com.pisces.service;

import com.pisces.pojo.MsComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pisces.util.Result;
import com.pisces.vo.Param.CommentParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
public interface MsCommentService extends IService<MsComment> {

    /**
     * @param id 通过i文章d查找所有的评论
     * @return
     */
    Result commentByArticltId(Long id);

    /**
     * @param commentParam 评论
     * @return
     */
    Result comMent(CommentParam commentParam);
}
