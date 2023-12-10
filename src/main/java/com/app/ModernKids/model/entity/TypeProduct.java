package com.app.ModernKids.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "types")
public class TypeProduct extends BaseEntity {
    @NotBlank
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "type")
    private Set<Product> products;
}
