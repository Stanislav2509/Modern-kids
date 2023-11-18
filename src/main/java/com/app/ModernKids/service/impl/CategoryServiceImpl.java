package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.repo.CategoryRepository;
import com.app.ModernKids.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
