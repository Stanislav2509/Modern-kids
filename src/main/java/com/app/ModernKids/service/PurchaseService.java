package com.app.ModernKids.service;

import com.app.ModernKids.model.dto.PurchaseBindingModel;
import com.app.ModernKids.model.dto.PurchaseDTO;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.Purchase;

import java.util.List;
import java.util.Set;

public interface PurchaseService {
    List<PurchaseDTO> getPurchases(String userEmail);

    void delete(Long id);

    double getCartPrice(List<PurchaseDTO> purchases);

    Purchase getPurchase(Product product, PurchaseBindingModel purchaseBindingModel, String userEmail);
    void addToCart(PurchaseBindingModel purchaseBindingModel);

    Set<Purchase> getAllPurchasesByUserEmail(String userEmail);

    List<PurchaseDTO> getProductsInCart();

    void delete(Long id, String age);

    void clearCart();
}
