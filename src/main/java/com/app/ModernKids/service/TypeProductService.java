package com.app.ModernKids.service;

import com.app.ModernKids.model.dto.AddTypeProductBindingModel;
import com.app.ModernKids.model.entity.TypeProduct;

import java.util.List;

public interface TypeProductService {
    List<TypeProduct> getAll();

    void add(AddTypeProductBindingModel addTypeProductBindingModel);

    boolean isUniqueTypeProduct(String value);

    TypeProduct getByName(String type);

    TypeProduct getById(Long typeId);
}
