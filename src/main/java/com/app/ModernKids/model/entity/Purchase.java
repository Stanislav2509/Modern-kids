package com.app.ModernKids.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.Set;

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
    private Status status;
    @ManyToOne
    private Order order;
    @ManyToOne
    private UserEntity user;
    private String age;
}
