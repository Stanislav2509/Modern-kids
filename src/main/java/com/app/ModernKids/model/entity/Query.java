package com.app.ModernKids.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "queries")
public class Query extends BaseEntity{
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Email
    @NotNull
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @NotNull
    private String message;

    @ManyToOne
    private Status status;
}
