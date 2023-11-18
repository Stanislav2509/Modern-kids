package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.AddTypeProductBindingModel;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.service.TypeProductService;
import com.app.ModernKids.repo.TypeProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeProductServiceImpl implements TypeProductService {
    private final TypeProductRepository typeProductRepository;

    public TypeProductServiceImpl(TypeProductRepository typeProductRepository) {
        this.typeProductRepository = typeProductRepository;
    }

    @Override
    public List<TypeProduct> getAll() {
        return typeProductRepository.findAll();
    }

    @Override
    public void add(AddTypeProductBindingModel addTypeProductBindingModel) {
        TypeProduct typeProduct = new TypeProduct();
        typeProduct.setName(addTypeProductBindingModel.getName());
        typeProductRepository.save(typeProduct);
    }

    @Override
    public boolean isUniqueTypeProduct(String name) {
        return typeProductRepository.findByName(name) == null;
    }
}
