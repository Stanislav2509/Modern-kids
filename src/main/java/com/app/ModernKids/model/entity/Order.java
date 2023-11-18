package com.app.ModernKids.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    private Double price;
    private Timestamp date;
    @ManyToOne
    private Status status;

    @OneToMany(mappedBy = "order")
    private Set<Purchase> purchases;
    @ManyToOne
    private UserEntity user;

}
