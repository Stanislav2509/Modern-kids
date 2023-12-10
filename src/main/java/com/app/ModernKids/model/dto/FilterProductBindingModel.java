package com.app.ModernKids.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterProductBindingModel {
    private List<String> brands;
    private int minPrice;
    private int maxPrice;
    private List<String> ages;
    private List<String> origins;
    private boolean makeClear = false;

    public void clear(){
        setAges(null);
        setBrands(null);
        setOrigins(null);
        setMakeClear(false);
        minPrice =  0;
        maxPrice = 0;
    }

}
