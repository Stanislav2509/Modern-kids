package com.app.ModernKids.model.entity;

import com.app.ModernKids.model.dto.AddCollectionBindingModel;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "collections")
public class Collection extends BaseEntity{
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "collection")
    private Set<Product> products;

}
