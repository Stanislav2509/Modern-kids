package com.app.ModernKids.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCollectionBindingModel {
    @NotBlank
    private String name;
}
