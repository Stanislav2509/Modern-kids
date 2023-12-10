package com.app.ModernKids.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseBindingModel {
    private Long productId;
    private String name;
    private Double price;
    private String imageURL;
    private int quantity = 1;
    private String age;
    private Double totalAmount;
}
