package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.AddTypeProductBindingModel;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.service.TypeProductService;
import com.app.ModernKids.repo.TypeProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public TypeProduct getByName(String type) {
        return typeProductRepository.findByName(type);
    }

    @Override
    public TypeProduct getById(Long typeId) {
        Optional<TypeProduct> typeProduct = typeProductRepository.findById(typeId);
        if(typeProduct.isPresent()) {
            TypeProduct type = new TypeProduct();
            type.setId(typeProduct.get().getId());
            type.setName(typeProduct.get().getName());
            return type;
        }
        return null;
    }
}
