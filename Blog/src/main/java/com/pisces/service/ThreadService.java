package com.pisces.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pisces.mapper.MsArticleMapper;
import com.pisces.pojo.MsArticle;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 线层池
 */
@Component
public class ThreadService {

    /**
     * @param msArticleMapper  在线层池中操作些数据 时期不影响主线程
     * @param msArticle
     */
    @Async("threadTaskExcutor")
    public void updateArticleViewCount(MsArticleMapper msArticleMapper, MsArticle msArticle) {
        Integer viewCounts = msArticle.getViewCounts();
        MsArticle updateArticle = new MsArticle();
        updateArticle.setViewCounts(viewCounts+1);

        LambdaQueryWrapper<MsArticle> msArticleLambdaQueryWrapper=new LambdaQueryWrapper<>();
        msArticleLambdaQueryWrapper.eq(MsArticle::getId,msArticle.getId());

        //设置一个为了在多线程的环境下，线层的安全
        msArticleLambdaQueryWrapper.eq(MsArticle::getViewCounts,viewCounts);
        msArticleMapper.update(updateArticle,msArticleLambdaQueryWrapper);
        try {
            //3秒
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
