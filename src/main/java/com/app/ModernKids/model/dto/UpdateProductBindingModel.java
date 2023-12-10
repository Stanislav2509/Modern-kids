package com.app.ModernKids.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateProductBindingModel {
    @NotNull
    @Min(1)
    private Double price;
    private List<String> ages;
    private List<Integer> counts;
    private String newAge;
    private Integer count;

}
