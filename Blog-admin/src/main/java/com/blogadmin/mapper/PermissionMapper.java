package com.blogadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogadmin.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper  extends BaseMapper<Permission> {
}
