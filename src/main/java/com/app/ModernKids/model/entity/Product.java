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
    @Column(unique = true)
    private String name;
    @Positive
    private Double price;
    @Column(name = "image_url", nullable = false)
    private String imageURL;
    @NotBlank
    private String brand;
    @NotBlank
    private String origin;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "products_ages",
//            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "age_id", referencedColumnName = "id")}
//    )
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "product")
    private Set<ProductAge> productAges;
    @ManyToOne
    private Category category;
    @ManyToOne
    private TypeProduct type;
    @ManyToOne
    private Collection collection;

    @OneToMany(mappedBy = "product")
    private Set<Purchase> purchases;
}
