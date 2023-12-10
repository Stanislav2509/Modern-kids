package com.app.ModernKids.repo;

import com.app.ModernKids.model.entity.Query;
import com.app.ModernKids.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {
    List<Query> findAllByStatus(Status status);
}
