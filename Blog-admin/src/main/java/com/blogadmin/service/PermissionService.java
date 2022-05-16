package com.blogadmin.service;

import com.blogadmin.model.params.PageParam;
import com.blogadmin.util.Result;
import org.springframework.stereotype.Service;

public interface PermissionService {
    /**
     *
     * @param pageParam  分页显示数据
     * @return
     */
    Result listPermission(PageParam pageParam);
}
