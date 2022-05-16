package com.pisces.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pisces.pojo.MsCategory;
import com.pisces.pojo.MsTag;
import com.pisces.mapper.MsTagMapper;
import com.pisces.service.MsTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pisces.util.Result;
import com.pisces.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
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
public class MsTagServiceImpl extends ServiceImpl<MsTagMapper, MsTag> implements MsTagService {

    @Autowired
    private  MsTagMapper msTagMapper;

    /**
     * @param articleId
     * @return
     */
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatisPuls 无法进行多表查询 需要自己写一个
       List<MsTag> msTagList = msTagMapper.findTagsByArticlsId(articleId);
        return copyList(msTagList);
    }

    /**
     *最热标签
     * @param limit
     * @return
     */
    @Override
    public Result maxTags(int limit) {
        /**
         * 1.标签上所拥有的最多文章数量即为最热标签
         * 2. 根据tag_id  分组 计数 从大到小进行排列 取前 limit个(6) 标签
         *  sql语句
         * select tag_id from ms_article_tag GROUP BY tag_id  ORDER BY count(*) DESC LIMIT 6
         */
         List<Long> tagsId  =  msTagMapper.findMaxHotTagId(limit);

         //判断是否为空 ，为空给予给空值
         if (CollectionUtils.isEmpty(tagsId)){
             return Result.success(Collections.emptyList());
         }
         /*
            tagsId不为空时进行
             需要的是 tag_id tag_name 和tag对象
          */
        List<MsTag> msTagList = msTagMapper.findTagsByTagId(tagsId);
        return Result.success(msTagList);
    }

    /**
     * @return 查询全部标签
     */
    @Override
    public Result allTag() {
        List<MsTag> msTagList = msTagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(msTagList)); 
    }

    /**
     * @return
     */
    @Override
    public Result allDetail() {
        LambdaQueryWrapper<MsTag> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.select(MsTag::getId,MsTag::getTagName);
        List<MsTag> msTags= msTagMapper.selectList(lambdaQueryWrapper);
        //转换数据 只有页面的需要的数据才显示
        return Result.success(copyList(msTags));
    }

    /**
     * @param id 通过标签id 展示对应文章
     * @return
     */
    @Override
    public Result tagDetailById(Long id) {
        MsTag msTag=msTagMapper.selectById(id);
        return Result.success(copy(msTag));
}

    private List<TagVo> copyList(List<MsTag> msTagList) {
        List<TagVo> tagVoList=new ArrayList<>();
        for (MsTag msTag:msTagList){
            tagVoList.add(copy(msTag));
        }
        return tagVoList;
    }

    public  TagVo copy(MsTag msTag){
        TagVo tagVo=new TagVo();
        BeanUtils.copyProperties(msTag,tagVo);
        tagVo.setId(String.valueOf(msTag.getId()));
        return  tagVo;
    }
}
