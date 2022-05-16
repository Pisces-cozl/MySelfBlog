package com.blogadmin.controller;


import com.blogadmin.model.params.PageParam;
import com.blogadmin.service.PermissionService;
import com.blogadmin.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Pisces
 * @since 2022-04-27
 */
@RestController
@RequestMapping("admin")
public class MsAdminController {
        @Autowired
        private PermissionService permissionService;

    @PostMapping
    public Result listPermission(@RequestBody PageParam pageParam){
        return  permissionService.listPermission(pageParam);
    }
}

