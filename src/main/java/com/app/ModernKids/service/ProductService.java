package com.app.ModernKids.service;

import com.app.ModernKids.model.dto.AddProductBindingModel;
import com.app.ModernKids.model.dto.FilterProductBindingModel;
import com.app.ModernKids.model.dto.ProductDTO;
import com.app.ModernKids.model.dto.UpdateProductBindingModel;
import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.model.view.ProductViewModel;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();


    void add(AddProductBindingModel addProductBindingModel);


    List<ProductViewModel> getAllByTypeAndCategory(TypeProduct type, Category category);

    List<ProductViewModel> getAllByCategoryId(Long categoryId);

    Product getById(Long productId);


    List<String> getBrands();

    List<String> getOrigins();

    List<ProductViewModel> filter(List<ProductViewModel> products, FilterProductBindingModel filterProductBindingModel);


    void updateById(Long id, UpdateProductBindingModel updatePictureBindingModel);

    List<ProductViewModel> getAllByCollection(String collectionName);
}
