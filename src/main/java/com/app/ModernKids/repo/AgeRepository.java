package com.app.ModernKids.repo;

import com.app.ModernKids.model.entity.Age;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgeRepository extends JpaRepository<Age, Long> {
    Age findByAge(String age);
}
