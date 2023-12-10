package com.app.ModernKids.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "products_ages")
public class ProductAge extends BaseEntity{
    @ManyToOne
    private Product product;
    @ManyToOne
    private Age age;
    private Integer count;
}
