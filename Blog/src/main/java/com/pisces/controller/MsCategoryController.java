package com.pisces.controller;


import com.pisces.service.MsCategoryService;
import com.pisces.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Pisces
 * @since 2022-04-21
 */
@RestController
@RequestMapping("categorys")
public class MsCategoryController {
    @Autowired
    private MsCategoryService msCategoryService;

    @GetMapping
    public Result categorys(){
        return msCategoryService.findAll();
    }

    @GetMapping("detail")
    public Result detail(){
        return  msCategoryService.allDetail();
    }

    @GetMapping("detail/{id}")
    public Result CategoryDetailById(@PathVariable("id") Long id){
        return  msCategoryService.CategoryDetailById(id);
    }

}

