package com.cl.service;

import com.cl.pojo.Category;

import java.util.List;

public interface CategoryService {

    void addCategory(Category category);

    List<Category> getList();

    Category findById(Integer id);

    void update(Category category);
}
