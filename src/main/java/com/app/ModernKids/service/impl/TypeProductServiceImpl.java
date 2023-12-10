package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.AddTypeProductBindingModel;
import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.ProductAge;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.model.enums.CategoryName;
import com.app.ModernKids.repo.ProductRepository;
import com.app.ModernKids.service.TypeProductService;
import com.app.ModernKids.repo.TypeProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TypeProductServiceImpl implements TypeProductService {
    private final TypeProductRepository typeProductRepository;
    private final ProductRepository productRepository;

    public TypeProductServiceImpl(TypeProductRepository typeProductRepository, ProductRepository productRepository) {
        this.typeProductRepository = typeProductRepository;
        this.productRepository = productRepository;
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

    @Override
    public boolean deleteById(Long id) {
        Optional<TypeProduct> typeOpt = typeProductRepository.findById(id);
        if(typeOpt.isPresent()) {
            TypeProduct typeProduct = typeOpt.get();
            List<Product> products = productRepository.findByType(typeProduct);

            if(!products.isEmpty()){
                return false;
            }

            typeProductRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public void updateById(Long id, AddTypeProductBindingModel addTypeProductBindingModel) {
        Optional<TypeProduct> typeOpt = typeProductRepository.findById(id);

        TypeProduct type = typeOpt.get();
        type.setName(addTypeProductBindingModel.getName());
        typeProductRepository.save(type);

    }

    @Override
    public List<TypeProduct> getByCategory(String displayValue) {
        List<TypeProduct> all = typeProductRepository.findAll();
        List<TypeProduct> result = new ArrayList<>();

        for (TypeProduct type : all) {
            Set<Product> products = type.getProducts();
            for (Product product : products) {
                String name = product.getCategory().getName();
                if(name.equals(displayValue) && !result.contains(type)){
                    result.add(type);
                }
            }


        }

        return result;

    }

    @Override
    public Map<String, List<TypeProduct>> getTypes() {
        List<TypeProduct> boys = getByCategory(CategoryName.BOY.getDisplayValue());
        List<TypeProduct> girls = getByCategory(CategoryName.GIRL.getDisplayValue());
        List<TypeProduct> babyBoys = getByCategory(CategoryName.BABY_BOY.getDisplayValue());
        List<TypeProduct> babyGirls = getByCategory(CategoryName.BABY_GIRL.getDisplayValue());

        Map<String, List<TypeProduct>> res = new LinkedHashMap<>();
        res.put(CategoryName.BOY.getDisplayValue(), boys);
        res.put(CategoryName.GIRL.getDisplayValue(), girls);
        res.put(CategoryName.BABY_BOY.getDisplayValue(), babyBoys);
        res.put(CategoryName.BABY_GIRL.getDisplayValue(), babyGirls);

        return res;
    }

}
