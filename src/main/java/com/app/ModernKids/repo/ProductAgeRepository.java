package com.app.ModernKids.repo;

import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.ProductAge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAgeRepository extends JpaRepository<ProductAge, Long> {
    List<ProductAge> getProductAgesByProduct(Product product);

    ProductAge getByProductAndAge(Product product, Age age);

     List<ProductAge> getByAge(Age age);
}
