package com.app.ModernKids.repo;

import com.app.ModernKids.model.entity.SessionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionUserRepository extends JpaRepository<SessionUser, Long> {
}
