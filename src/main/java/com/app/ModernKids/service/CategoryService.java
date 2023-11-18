package com.app.ModernKids.service;

import com.app.ModernKids.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category getByName(String name);

    Category getById(Long categoryId);

}
