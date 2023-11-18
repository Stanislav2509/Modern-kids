package com.app.ModernKids.model.dto;

import com.app.ModernKids.validation.anotations.UniqueTypeProduct;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTypeProductBindingModel {
    @NotBlank
    @UniqueTypeProduct
    private String name;
}
