package com.app.ModernKids.repo;

import com.app.ModernKids.model.entity.UserRole;
import com.app.ModernKids.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(UserRoleEnum userRoleEnum);
}
