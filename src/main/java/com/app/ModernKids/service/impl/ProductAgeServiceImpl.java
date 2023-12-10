package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.ProductAgeDTO;
import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.ProductAge;
import com.app.ModernKids.repo.AgeRepository;
import com.app.ModernKids.repo.ProductAgeRepository;
import com.app.ModernKids.repo.ProductRepository;
import com.app.ModernKids.service.ProductAgeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductAgeServiceImpl implements ProductAgeService {
    private final ProductAgeRepository productAgeRepository;
    private final AgeRepository ageRepository;
    private final ProductRepository productRepository;


    public ProductAgeServiceImpl(ProductAgeRepository productAgeRepository, AgeRepository ageRepository, ProductRepository productRepository) {
        this.productAgeRepository = productAgeRepository;
        this.ageRepository = ageRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Age> getAgesOnProduct(Product product) {
        List<ProductAge> productAgesByProduct = productAgeRepository.getProductAgesByProduct(product);
        List<Age> ages = new ArrayList<>();

        for (ProductAge productAge: productAgesByProduct) {
            Age age = productAge.getAge();
            ages.add(age);
        }
        return ages;
    }

    @Override
    public ProductAge getByProduct(Product product, String ageName) {
        Age age = ageRepository.findByAge(ageName);
        return productAgeRepository.getByProductAndAge(product, age);

    }

    @Override
    public Map<String, Integer> getAgeCount(Product product) {
        Map<String, Integer> ageCount = new LinkedHashMap<>();
        List<ProductAge> productAges = productAgeRepository.getProductAgesByProduct(product);
        for (ProductAge productAge: productAges) {
            ageCount.put(productAge.getAge().getAge(), productAge.getCount());
        }
        return ageCount;
    }

    @Override
    public List<ProductAgeDTO> getAll() {
        List<ProductAgeDTO> productAgeDTOS = new ArrayList<>();
        List<ProductAge> all = productAgeRepository.findAll();

        for (ProductAge productAge : all) {
            Product product = productRepository.findByName(productAge.getProduct().getName());
            Age age = ageRepository.findByAge(productAge.getAge().getAge());
            ProductAgeDTO productAgeDTO = new ProductAgeDTO();
            productAgeDTO.setAge(age);
            productAgeDTO.setProduct(product);
            productAgeDTO.setCount(productAge.getCount());
            productAgeDTOS.add(productAgeDTO);
        }
        return productAgeDTOS;
    }
}
