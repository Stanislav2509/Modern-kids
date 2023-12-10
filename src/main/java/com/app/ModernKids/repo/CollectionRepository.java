package com.app.ModernKids.repo;

import com.app.ModernKids.model.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Collection getByName(String collectionName);
}
