package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.repo.CategoryRepository;
import com.app.ModernKids.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Category getByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category getById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isPresent()){
            Category category = new Category();
            category.setName(categoryOptional.get().getName());
            category.setId(categoryOptional.get().getId());
            return category;
        }
        return null;
    }
}
