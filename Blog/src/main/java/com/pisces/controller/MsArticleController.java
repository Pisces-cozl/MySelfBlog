package com.pisces.controller;


import com.pisces.common.aop.LogAnnotation;
import com.pisces.common.cache.Cache;
import com.pisces.service.MsArticleService;
import com.pisces.util.Result;
import com.pisces.vo.Param.ArticleParam;
import com.pisces.vo.Param.PageParams;
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
@RequestMapping("articles")
public class MsArticleController {

    @Autowired
    private MsArticleService msArticleService;

    /**
     *  首页文章列表
     */
    @PostMapping
    @LogAnnotation(module = "文章" ,operator = "获取文章列表")
    @Cache(expire = 5*60*1000,name = "list_article")
    public Result listArticle(@RequestBody PageParams pageParams){

        return  msArticleService.listArticle(pageParams);
     }

    /**
     * 最首页热文章
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5*60*1000,name = "hot_article")
    public Result maxHot(){
        //查询最热的6个文章
        int limit = 6 ;
        return  msArticleService.maxArticle(limit);
    }

    /**
     * 首页最新文章
     * @return
     */
    @PostMapping("new")
    @Cache(expire = 5*60*1000,name = "news_article")
    public Result maxNew(){
        //查询最热的6个文章
        int limit = 6 ;
        return  msArticleService.maxNewArticle(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public  Result listArchives(){
        return  msArticleService.listArchives();
    }

    /**
     * 通过文章id查找到相对应的内容
     * @param id
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArchivesViewById(@PathVariable("id") long id){
        return  msArticleService.findArchivesViewById(id);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return  msArticleService.publish(articleParam);
    }
}

