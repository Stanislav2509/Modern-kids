package com.app.ModernKids.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "ages")
public class Age extends BaseEntity{
    @NotBlank
    private String age;
}
