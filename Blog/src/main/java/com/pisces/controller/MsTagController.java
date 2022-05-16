package com.pisces.controller;


import com.pisces.service.MsTagService;
import com.pisces.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@RestController
@RequestMapping("tags")
public class MsTagController {
    @Autowired
    private MsTagService msTagService;


    /**
     * 最热标签
     * @return
     */
    @GetMapping("hot")
    public Result maxHot(){
        //查询最热的6个标签
        int limit =6;
      return  msTagService.maxTags(limit);
    }

    /**
     * @return 查询全部标签
     */
    @GetMapping
    public Result AllTag(){
        return  msTagService.allTag();
    }

    @GetMapping("detail")
    public Result detail(){
     return   msTagService.allDetail();
    }

    @GetMapping("detail/{id}")
    public Result tagDetailById(@PathVariable("id") Long id){
        return   msTagService.tagDetailById(id);
    }
}

