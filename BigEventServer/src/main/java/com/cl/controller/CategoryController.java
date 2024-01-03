package com.cl.controller;

import com.cl.pojo.Category;
import com.cl.pojo.Result;
import com.cl.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result addCategory(@RequestBody @Validated Category category){
        categoryService.addCategory(category);
        return Result.success("添加文章分类成功！");
    }

    @GetMapping
    public Result<List<Category>> list(){
        List<Category> list = categoryService.getList();
        return Result.success(list);
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id){
        Category c = categoryService.findById(id);
        return Result.success(c);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Category category){
        categoryService.update(category);
        return Result.success("更新文章分类成功！");
    }


}
