package com.app.ModernKids.model.dto;

import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.ProductAge;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAgeDTO {
    private Product product;
    private Age age;
    private Integer count;
}
