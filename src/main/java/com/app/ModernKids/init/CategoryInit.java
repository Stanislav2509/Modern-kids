package com.app.ModernKids.init;

import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.model.enums.CategoryName;
import com.app.ModernKids.repo.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CategoryInit implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    public CategoryInit(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        long countCategories = categoryRepository.count();

        if(countCategories == 0) {
            List<Category> categories = new ArrayList<>();
            Arrays.stream(CategoryName.values()).forEach(categoryName -> {
                Category category = new Category();
                category.setName(categoryName.getDisplayValue());
                categories.add(category);
            });

            categoryRepository.saveAll(categories);
        }


    }
}
