package com.pisces.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pisces.mapper.MsArticleBodyMapper;
import com.pisces.mapper.MsArticleTagMapper;
import com.pisces.pojo.MsArticle;
import com.pisces.mapper.MsArticleMapper;
import com.pisces.pojo.MsArticleBody;
import com.pisces.pojo.MsArticleTag;
import com.pisces.pojo.MsSysUser;
import com.pisces.pojo.dos.Archives;
import com.pisces.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pisces.util.Result;
import com.pisces.util.UserThreadLocal;
import com.pisces.vo.ArticleBodyVo;
import com.pisces.vo.ArticleVo;
import com.pisces.vo.Param.ArticleParam;
import com.pisces.vo.Param.PageParams;
import com.pisces.vo.TagVo;
import com.pisces.vo.UserVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@Service
public class MsArticleServiceImpl extends ServiceImpl<MsArticleMapper, MsArticle> implements MsArticleService {
    @Autowired
    private MsSysUserService msSysUserService;

    @Autowired
    private MsTagService msTagService;

    @Autowired
    private MsCategoryService msCategoryService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private MsArticleTagMapper msArticleTagMapper;

    @Autowired
    private MsArticleBodyMapper msArticleBodyMapper;

    @Autowired
    private  MsArticleMapper msArticleMapper;



    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    @Override
    public Result listArticle(PageParams pageParams) {
            /*
             1.分页查询article数据库表 得到结果
             */

        //使用mybatispulsPage方法 参数传入当前页数和每页查询的数量
        Page<MsArticle> page=new Page(pageParams.getPage(),pageParams.getPageSize());

        LambdaQueryWrapper<MsArticle> msArticleLambdaQueryWrapper=new LambdaQueryWrapper<>();
        if (pageParams.getCategoryId()!=null){
            msArticleLambdaQueryWrapper.eq(MsArticle::getCategoryId,pageParams.getCategoryId());
        }
        List<Long> articleIdList =new ArrayList<>();
        if (pageParams.getTagId()!=null){
            LambdaQueryWrapper<MsArticleTag> msArticleTagLambdaQueryWrapper=new LambdaQueryWrapper<>();
            msArticleTagLambdaQueryWrapper.eq(MsArticleTag::getId,pageParams.getTagId());
            List<MsArticleTag> msArticleTags = msArticleTagMapper.selectList(msArticleTagLambdaQueryWrapper);
            for (MsArticleTag msArticleTag : msArticleTags ) {
                    articleIdList.add(msArticleTag.getId());
            }
            if (articleIdList.size()>0){
                msArticleLambdaQueryWrapper.in(MsArticle::getId,articleIdList);
            }
        }
        //是否置顶排序
        msArticleLambdaQueryWrapper.orderByDesc(MsArticle::getWeight);

        //根据时间进行查询 采用倒序排列
        msArticleLambdaQueryWrapper.orderByDesc(MsArticle::getCreateDate);

        Page<MsArticle> msArticlePage = msArticleMapper.selectPage(page, msArticleLambdaQueryWrapper);

        //遍历并返回
        List<MsArticle> records = msArticlePage.getRecords();

        //进行复制返回
        List<ArticleVo> articleVos=copyList(records,true,true);
        return Result.success(articleVos);
    }

//    @Override
//    public Result listArticle(PageParams pageParams) {
//        Page<MsArticle> page=new Page(pageParams.getPage(),pageParams.getPageSize());
//        System.out.println(pageParams.getCategoryId());
//        IPage<MsArticle> msArticleIPage = msArticleMapper.listArticle(page,
//                                                                        pageParams.getCategoryId(),
//                                                                        pageParams.getTagId(),
//                                                                        pageParams.getYear(),
//                                                                        pageParams.getMonth());
//
//        List<MsArticle> records = msArticleIPage.getRecords();
//
//        return Result.success(copyList(records,true,true));
//    }

    /**
     * 最热文章
     * @param limit
     * @return
     */
    @Override
    public Result maxArticle(int limit) {
        LambdaQueryWrapper<MsArticle> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(MsArticle::getViewCounts);
        lambdaQueryWrapper.select(MsArticle::getId,MsArticle::getTitle);
        lambdaQueryWrapper.last("limit " +limit);
        // select id ,title from article order by view_counts desc limit 6

        List<MsArticle> msArticles = msArticleMapper.selectList(lambdaQueryWrapper);

        return Result.success(copyList(msArticles,false,false));
    }

    /**
     * 首页最新文章
     * @param limit
     * @return
     */
    @Override
    public Result maxNewArticle(int limit) {
        LambdaQueryWrapper<MsArticle> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(MsArticle::getCreateDate);
        lambdaQueryWrapper.select(MsArticle::getId,MsArticle::getTitle);
        lambdaQueryWrapper.last("limit "+limit);
        // select id ,title from article order by getCreateDate desc limit 6

        List<MsArticle> msArticles = msArticleMapper.selectList(lambdaQueryWrapper);
        return Result.success(copyList(msArticles,false,false));
    }

    /**
     * 文章归档
     * @return
     */
    @Override
    public Result listArchives() {
        List<Archives>  archivesList =msArticleMapper.listArchives();

        return Result.success(archivesList);
    }


    /**
     * @param id 通过文章id查找到相对应的文章内容
     * @return
     */
    @Override
    public Result findArchivesViewById(long id) {
        /**
         * 1.根据id 查找文章信息
         * 2.根据文章内容id和分类id做关联
         */
        MsArticle msArticle=msArticleMapper.selectById(id);
        ArticleVo articleVo = copy(msArticle,true,true, true, true);
        //查看文章增加阅读数量
        //查看文章，直接返回数据 更新时加写锁，会阻塞其他读取操作，性能有所下降
        //使用线层池   可以把更新操作放入线层池中执行，与主线程分开
        threadService.updateArticleViewCount(msArticleMapper,msArticle);
        return Result.success(articleVo);
    }

    /**
     * @param articleParam 发布文章
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {
        //配置到拦截当中 发布文章只有登录后才能发布
        MsSysUser msSysUser= UserThreadLocal.getMsSysUser();
        /**
         * 1.发布文章 需要构建article对象
         * 2.作者id,当前的登录用户
         * 3.获取标签
         * 4.内容存储
         */
        MsArticle article=new MsArticle();
        article.setAuthorId(msSysUser.getId());
        article.setWeight(MsArticle.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        //增加一个文章 增加后会有一个文章id
        msArticleMapper.insert(article);
        //tag
        List<TagVo> tags = articleParam.getTags();
        if (tags!=null){
            for (TagVo tagVo: tags) {
                Long id = article.getId();
                MsArticleTag msArticleTag = new MsArticleTag();
                msArticleTag.setArticleId(id);
                msArticleTag.setTagId(Long.parseLong(tagVo.getId()));
                msArticleTagMapper.insert(msArticleTag);
            }
        }
        //body\
        MsArticleBody msArticleBody = new MsArticleBody();
        msArticleBody.setArticleId(article.getId());
        msArticleBody.setContent(articleParam.getBody().getContent());
        msArticleBody.setContentHtml(articleParam.getBody().getContentHtml());
        msArticleBodyMapper.insert(msArticleBody);
        article.setBodyId(msArticleBody.getId());
        msArticleMapper.updateById(article);

        Map<String,String> map =new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
    }

    /*
        进行数据转移
     */
    private List<ArticleVo> copyList(List<MsArticle> records,boolean isTag , boolean isAuthor) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (MsArticle article: records) {
            articleVoList.add(copy(article,isTag,isAuthor,false,false));
        }

        return  articleVoList;
    }

    private List<ArticleVo> copyList(List<MsArticle> records,boolean isTag , boolean isAuthor,boolean isBody ) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (MsArticle article: records) {
            articleVoList.add(copy(article,isTag,isAuthor,isBody,false));
        }
        return  articleVoList;
    }

    private List<ArticleVo> copyList(List<MsArticle> records,boolean isTag , boolean isAuthor,boolean isBody , boolean isCategory) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (MsArticle article: records) {
            articleVoList.add(copy(article,isTag,isAuthor,isBody,isCategory));
        }
        return  articleVoList;
    }


    /**
     * @param msArticle
     * @param isBody
     * @param isCategory
     * @return
     */
    private  ArticleVo copy(MsArticle msArticle,boolean isTag , boolean isAuthor,boolean isBody , boolean isCategory){
        ArticleVo articleVo =new ArticleVo();
//        BeanUtils.copyProperties(msArticle,articleVo);
//        articleVo.setId(msArticle.getId());
        articleVo.setId(String.valueOf(msArticle.getId()));
        BeanUtils.copyProperties(msArticle, articleVo);

        //因为数据库当中的时间是属于Long型  articleVo里是String 所以进行一个转行
        articleVo.setCreateDate(new DateTime(msArticle.getCreateDate()).toString("yyy-MM-dd HH:mm"));

        BeanUtils.copyProperties(msArticle, articleVo);
        if (isTag){
            //因为标签是属于文章的 获取文章的id
            Long id = msArticle.getId();
            articleVo.setTags(msTagService.findTagsByArticleId(id));
        }
        if (isAuthor){
            Long authorId = msArticle.getAuthorId();
            MsSysUser msSysUser=msSysUserService.findUserById(authorId);
            UserVo userVo=new UserVo();
            userVo.setId(String.valueOf(msArticle.getId()));
            userVo.setNickname(msSysUser.getNickname());
            userVo.setAvatar(msSysUser.getAvatar());
            articleVo.setAuthor(userVo);
        }

        if (isBody){
            Long bodyId = msArticle.getBodyId();
            System.out.println(msArticle.getBodyId());
            articleVo.setBody(findArticleBodyById(bodyId));


        }
        if (isCategory){
            Long categoryId = msArticle.getCategoryId();
            articleVo.setCategory(msCategoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    /**
     * @param id 根据文章内容id查找对应的内容
     * @return
     */
    private ArticleBodyVo findArticleBodyById(Long id) {
        MsArticleBody msArticleBody = msArticleBodyMapper.selectById(id);

        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(msArticleBody.getContent());
        return articleBodyVo;
    }


}
