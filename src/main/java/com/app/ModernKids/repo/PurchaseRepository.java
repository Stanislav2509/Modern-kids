package com.app.ModernKids.repo;

import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.Purchase;
import com.app.ModernKids.model.entity.Status;
import com.app.ModernKids.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Set<Purchase> getAllByUserAndStatus(UserEntity user, Status status);

    Optional<Purchase> getByProductAndUserAndAgeAndStatus(Product product, UserEntity user, String age, Status status);
}
