package com.app.ModernKids.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "purchases")
public class Purchase extends BaseEntity{
    @Positive
    private Integer count;
    @Column(name = "total_amaunt", nullable = false)
    private Double totalAmount;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Order order;
}
