package com.pisces.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pisces.pojo.MsArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pisces.pojo.dos.Archives;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@Mapper
public interface MsArticleMapper extends BaseMapper<MsArticle> {

    /**
     * @return  文章归档
     */
    List<Archives> listArchives();

    IPage<MsArticle> listArticle(Page<MsArticle> page,
                                 Long categoryId,
                                 Long tagId,
                                 String tear,
                                 String month);
}
