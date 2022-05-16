package com.pisces.service;

import com.pisces.pojo.MsArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pisces.util.Result;
import com.pisces.vo.Param.ArticleParam;
import com.pisces.vo.Param.PageParams;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@Service
public interface MsArticleService extends IService<MsArticle> {

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result maxArticle(int limit);

    /**
     * 首页最新文章
     * @param limit
     * @return
     */
    Result maxNewArticle(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     *
     * @param id 通过文章id查找到相对应的文章内容
     * @return
     */
    Result findArchivesViewById(long id);

    /**
     * @param articleParam 发布文章
     * @return
     */
    Result publish(ArticleParam articleParam);
}
