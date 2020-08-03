package com.fh.category.controller;

import com.fh.category.service.CategoryService;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("categoryController")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @RequestMapping("queryList")
    @Ignore
    private ServerResponse queryList(){
        List<Map<String,Object>> list = categoryService.queryList();
        return  ServerResponse.success(list);
    }
}
