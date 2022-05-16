package com.pisces.service;

import com.pisces.pojo.MsCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pisces.util.Result;
import com.pisces.vo.CategoryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
public interface MsCategoryService extends IService<MsCategory> {

    /**
     * @param categoryId  根据类别id查找类别
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);

    /**
     * @return  所以的类别查询
     */
    Result findAll();

    /**
     * @return
     */
    Result allDetail();

    /**
     * @param id  文章分类显示 通过id
     * @return
     */
    Result CategoryDetailById(Long id);
}
