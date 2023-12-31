package com.app.ModernKids.model.entity;

import com.app.ModernKids.model.enums.CategoryName;
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
@Table(name = "categories")
public class Category extends BaseEntity{
    @NotBlank
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private Set<Product> products;
}
