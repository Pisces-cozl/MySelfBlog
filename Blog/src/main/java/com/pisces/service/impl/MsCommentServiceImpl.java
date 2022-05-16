package com.pisces.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pisces.pojo.MsArticle;
import com.pisces.pojo.MsComment;
import com.pisces.mapper.MsCommentMapper;
import com.pisces.pojo.MsSysUser;
import com.pisces.service.MsCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pisces.service.MsSysUserService;
import com.pisces.util.Result;
import com.pisces.util.UserThreadLocal;
import com.pisces.vo.ArticleVo;
import com.pisces.vo.CommentVo;
import com.pisces.vo.Param.CommentParam;
import com.pisces.vo.UserVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@Service
public class MsCommentServiceImpl extends ServiceImpl<MsCommentMapper, MsComment> implements MsCommentService {
        @Autowired
        private MsSysUserService msSysUserService;

        @Autowired
        private MsCommentMapper msCommentMapper;

    /**
     * @param id 通过i文章d查找所有的评论
     * @return
     */
    @Override
    public Result commentByArticltId(Long id) {
        /**
         * 1.根据文章id查询文章列表
         * 2.根据作者id查询作者信息
         * 3.判断 如果 level=1 要去查询有没有子评论
         * 4.如果有 根据评论id进行查询 (parent_id)
         */
        LambdaQueryWrapper<MsComment> msCommentLambdaQueryWrapper=new LambdaQueryWrapper<>();
        msCommentLambdaQueryWrapper.eq(MsComment::getArticleId,id);
        msCommentLambdaQueryWrapper.eq(MsComment::getLevel,1);
        List<MsComment> msComments = msCommentMapper.selectList(msCommentLambdaQueryWrapper);

        List<CommentVo> commentVos=copyList(msComments);
        return Result.success(commentVos);
    }

    /**
     * @param commentParam 评论
     * @return
     */
    @Override
    public Result comMent(CommentParam commentParam) {
        MsSysUser msSysUser = UserThreadLocal.getMsSysUser();

        MsComment comment = new MsComment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(msSysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());

        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }

        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();

        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.msCommentMapper.insert(comment);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<MsComment> msComments) {
        List<CommentVo> commentVoListi=new ArrayList<>();
        for (MsComment msComment : msComments) {
            commentVoListi.add(copy(msComment));
        }
        return null;
    }

    /**
     * @param msComment
     * @return
     */
    private CommentVo copy(MsComment msComment) {
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(msComment,commentVo);
        commentVo.setId(String.valueOf(msComment.getId()));
        //作者信息
        Long authorId = msComment.getAuthorId();
        UserVo sysUserById = msSysUserService.findSysUserById(authorId);
        commentVo.setAuthor(sysUserById);

        //子评论
        Integer level = msComment.getLevel();
        if (1 == level){
            Long id = msComment.getId();
            List<CommentVo> commentVoList=findCommentByParentId(id);
            commentVo.setChildrens(commentVoList);
        }

        //给谁评论
        if (level>1) {
            Long toUid = msComment.getToUid();
            UserVo toUidVo= msSysUserService.findSysUserById(toUid);
            commentVo.setToUser(toUidVo);
        }
            return commentVo;
    }


    /**
     * 子评论查询
     * @param id
     * @return
     */
    private List<CommentVo> findCommentByParentId(Long id) {
        LambdaQueryWrapper<MsComment> msCommentLambdaQueryWrapper=new LambdaQueryWrapper<>();
        msCommentLambdaQueryWrapper.eq(MsComment::getParentId,id);
        msCommentLambdaQueryWrapper.eq(MsComment::getLevel,2);

        return copyList(msCommentMapper.selectList(msCommentLambdaQueryWrapper));
    }
}
