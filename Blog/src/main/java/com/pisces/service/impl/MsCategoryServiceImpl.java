package com.pisces.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.pisces.pojo.MsCategory;
import com.pisces.mapper.MsCategoryMapper;
import com.pisces.service.MsCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pisces.util.Result;
import com.pisces.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class MsCategoryServiceImpl extends ServiceImpl<MsCategoryMapper, MsCategory> implements MsCategoryService {

    @Autowired
    private MsCategoryMapper msCategoryMapper;
    /**
     * @param categoryId 根据类别id查找类别
     * @return
     */
    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        MsCategory msCategory = msCategoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(msCategory,categoryVo);
        categoryVo.setId(String.valueOf(msCategory.getId()));
//        MsCategory msCategory = msCategoryMapper.selectById(categoryId);
//        CategoryVo categoryVo = new CategoryVo();
//        categoryVo.setId(msCategory.getId().toString());
//        categoryVo.setCategoryName(msCategory.getCategoryName());
//        categoryVo.setAvatar(msCategory.getAvatar());
        return categoryVo;
    }

    /**
     * @return  所有的类别查询
     */
    @Override
    public Result findAll() {
        LambdaQueryWrapper<MsCategory> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.select(MsCategory::getId,MsCategory::getCategoryName);
        List<MsCategory> msCategories = msCategoryMapper.selectList(lambdaQueryWrapper);
        //转换数据 只有页面的需要的数据才显示
        return Result.success(copyList(msCategories));
    }

    /**
     * @return
     */
    @Override
    public Result allDetail() {
        LambdaQueryWrapper<MsCategory> lambdaQueryWrapper=new LambdaQueryWrapper();
        List<MsCategory> msCategories = msCategoryMapper.selectList(lambdaQueryWrapper);
        //转换数据 只有页面的需要的数据才显示
        return Result.success(copyList(msCategories));
    }

    /**
     * @param id 文章分类显示 通过id
     * @return
     */
    @Override
    public Result CategoryDetailById(Long id) {
        MsCategory msCategory = msCategoryMapper.selectById(id);

        return Result.success(copy(msCategory));
    }

    private List<CategoryVo> copyList(List<MsCategory> msCategories) {
        List<CategoryVo> categoryVos=new ArrayList<>();
        for (MsCategory msCategory:msCategories){
            categoryVos.add(copy(msCategory));
        }
        return  categoryVos;
    }

    private CategoryVo copy(MsCategory msCategories ) {
            CategoryVo categoryVo=new CategoryVo();
            BeanUtils.copyProperties(msCategories,categoryVo);
            categoryVo.setId(String.valueOf(msCategories.getId()));
        return categoryVo;
    }


}
