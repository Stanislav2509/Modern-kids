package com.app.ModernKids.repo;

import com.app.ModernKids.model.entity.Order;
import com.app.ModernKids.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getByStatus(Status status);
}
