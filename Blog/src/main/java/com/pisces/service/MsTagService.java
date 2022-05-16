package com.pisces.service;

import com.pisces.pojo.MsTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pisces.util.Result;
import com.pisces.vo.ArticleBodyVo;
import com.pisces.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
public interface MsTagService extends IService<MsTag> {

    /**
     * 通过文章Id查找对于的标签
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);


    /**
     * 查找最热的标签
     * @param limit
     * @return
     */
    Result maxTags(int limit);


    /**
     * @return  查询全部标签
     */
    Result allTag();

    /**
     * @return
     */
    Result allDetail();

    /**
     * @param id  通过标签id 展示对应文章
     * @return
     */
    Result tagDetailById(Long id);
}
