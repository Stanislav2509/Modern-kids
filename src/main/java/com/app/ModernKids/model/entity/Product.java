package com.app.ModernKids.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    @NotBlank
    private String name;
    @Positive
    private Double price;
    @Column(name = "image_url", nullable = false)
    private String imageURL;
    @NotBlank
    private String brand;
    @Positive
    private int count;
    @NotBlank
    private String origin;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "products_ages",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "age_id", referencedColumnName = "id")}
    )
    private Set<Age> ages;
    @ManyToOne
    private Category category;
    @ManyToOne
    private TypeProduct type;

    @OneToMany(mappedBy = "product")
    private Set<Purchase> purchases;
}
