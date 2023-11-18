package com.app.ModernKids.service;

import com.app.ModernKids.model.dto.AddProductBindingModel;
import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.model.view.ProductViewModel;

import java.util.List;

public interface ProductService {
    List<Product> getAll();


    void add(AddProductBindingModel addProductBindingModel);


    List<ProductViewModel> getAllByTypeAndCategory(TypeProduct type, Category category);

    List<ProductViewModel> getAllByCategoryId(Long categoryId);
}
