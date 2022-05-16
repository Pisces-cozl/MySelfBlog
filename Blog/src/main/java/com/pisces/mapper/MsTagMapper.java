package com.pisces.mapper;

import com.pisces.pojo.MsTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
public interface MsTagMapper extends BaseMapper<MsTag> {


    /**
     * 多表查询 通过文章ID查找对应的标签
     * @return
     */
    List<MsTag> findTagsByArticlsId(Long articleId);


    /**
     *  查找最热的前几个标签
     * @param limit
     * @return
     */
    List<Long> findMaxHotTagId(int limit);

    /**
     * 根据tagId查到Tag信息
     * @param tagsId
     * @return
     */
    List<MsTag> findTagsByTagId(List<Long> tagsId);
}
