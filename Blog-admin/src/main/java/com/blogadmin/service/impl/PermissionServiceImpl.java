package com.blogadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blogadmin.mapper.PermissionMapper;
import com.blogadmin.model.params.PageParam;

import com.blogadmin.pojo.Permission;
import com.blogadmin.service.PermissionService;
import com.blogadmin.util.PageResult;
import com.blogadmin.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl  implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * @param pageParam 分页显示数据
     * @return
     */
    @Override
    public Result listPermission(PageParam pageParam) {
        Page<Permission> page=new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> permissionLambdaQueryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageParam.getQueryString())){
            permissionLambdaQueryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, permissionLambdaQueryWrapper);
        PageResult<Permission> permissionPageResult=new PageResult<>();
        permissionPageResult.setList(permissionPage.getRecords());
        permissionPageResult.setTotel(permissionPage.getTotal());
        return Result.success(permissionPageResult);
    }
}
