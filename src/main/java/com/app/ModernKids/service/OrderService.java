package com.app.ModernKids.service;

import com.app.ModernKids.model.dto.OrderDTO;
import com.app.ModernKids.model.dto.PurchaseDTO;
import com.app.ModernKids.model.entity.Purchase;

import java.util.List;
import java.util.Set;

public interface OrderService {
    boolean makeOrder(String userEmail);


    void updateStatus(Long id, String name);

    List<OrderDTO> getOrderByStatus(String statusName);

}
