package com.app.ModernKids.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "ages")
public class Age extends BaseEntity{
    @NotBlank
    private String age;
    @OneToMany(mappedBy = "age")
    private Set<ProductAge> productAges;

}
