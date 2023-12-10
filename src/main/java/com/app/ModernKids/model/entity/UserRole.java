package com.app.ModernKids.model.entity;

import com.app.ModernKids.model.enums.UserRoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "roles")
public class UserRole extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public UserRole setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
