package com.app.ModernKids.model.dto;

import com.app.ModernKids.model.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseDTO {
    private Long purchaseId;
    private Integer count;
    private Double totalAmount;
    private Product product;
}
