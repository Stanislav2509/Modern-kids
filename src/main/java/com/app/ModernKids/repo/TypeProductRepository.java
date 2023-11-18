package com.app.ModernKids.repo;

import com.app.ModernKids.model.entity.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.Optional;

@Repository
public interface TypeProductRepository extends JpaRepository<TypeProduct, Long> {
   TypeProduct findByName(String name);
}
