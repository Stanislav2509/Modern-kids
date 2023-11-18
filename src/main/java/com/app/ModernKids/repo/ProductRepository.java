package com.app.ModernKids.repo;

import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.model.view.ProductViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByTypeAndCategory(TypeProduct type, Category category);

    List<Product> findAllByCategory(Category category);
}
