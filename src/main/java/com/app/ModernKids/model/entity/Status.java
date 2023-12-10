package com.app.ModernKids.model.entity;

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
@Table(name = "statuses")
public class Status extends BaseEntity {
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "status")
    private Set<Order> orders;
    @OneToMany(mappedBy = "status")
    private Set<Purchase> purchases;
    @OneToMany(mappedBy = "status")
    private Set<Query> queries;
}
