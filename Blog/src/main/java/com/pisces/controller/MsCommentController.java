package com.pisces.controller;


import com.pisces.service.MsCommentService;
import com.pisces.util.Result;
import com.pisces.vo.Param.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@RestController
@RequestMapping("comments")
public class MsCommentController {
    @Autowired
    private MsCommentService commentService;

    @GetMapping("article/{id}")
    public Result comment(@PathVariable("id") Long id){
        return commentService.commentByArticltId(id);
    }

    @PostMapping("create/change")
    public Result comMent(@RequestBody CommentParam commentParam){
         return  commentService.comMent(commentParam);
    }
}

