package com.app.ModernKids.service;

import com.app.ModernKids.model.dto.ProductAgeDTO;
import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.ProductAge;

import java.util.List;
import java.util.Map;

public interface ProductAgeService {
    List<Age> getAgesOnProduct(Product product);

    ProductAge getByProduct(Product product, String age);

    Map<String, Integer> getAgeCount(Product product);

    List<ProductAgeDTO> getAll();

}
