package com.app.ModernKids.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartBindingModel {
    List<PurchaseBindingModel> purchases;
}
