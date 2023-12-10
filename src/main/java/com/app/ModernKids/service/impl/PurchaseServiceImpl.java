package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.PurchaseBindingModel;
import com.app.ModernKids.model.dto.PurchaseDTO;
import com.app.ModernKids.model.entity.*;
import com.app.ModernKids.model.enums.StatusName;
import com.app.ModernKids.repo.*;
import com.app.ModernKids.service.PurchaseService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final ProductAgeRepository productAgeRepository;
    private final AgeRepository ageRepository;



    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, UserRepository userRepository, StatusRepository statusRepository, ProductAgeRepository productAgeRepository, AgeRepository ageRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.productAgeRepository = productAgeRepository;
        this.ageRepository = ageRepository;
    }

    @Override
    public List<PurchaseDTO> getPurchases(String userEmail) {
        List<PurchaseDTO> purchaseDTOS = new ArrayList<>();
        UserEntity user = userRepository.findByEmail(userEmail).get();
        Status status = statusRepository.getByName(StatusName.CART.getDisplayValue());
        Set<Purchase> allByUserAndStatus = purchaseRepository.getAllByUserAndStatus(user, status);

        for (Purchase purchase : allByUserAndStatus) {
            PurchaseDTO purchaseDTO = new PurchaseDTO();
            purchaseDTO.setProduct(purchase.getProduct());
            purchaseDTO.setCount(purchase.getCount());
            purchaseDTO.setTotalAmount(purchase.getTotalAmount());
            purchaseDTO.setPurchaseId(purchase.getId());
            purchaseDTOS.add(purchaseDTO);
        }
        return purchaseDTOS;
    }

    @Override
    public void delete(Long id) {
        purchaseRepository.deleteById(id);
    }

    @Override
    public double getCartPrice(List<PurchaseDTO> purchases) {
        double sum = 0;
        for (PurchaseDTO purchase : purchases) {
            sum += purchase.getTotalAmount();
        }
        return sum;
    }

    @Override
    public Purchase getPurchase(Product product, PurchaseBindingModel purchaseBindingModel, String userEmail) {
        int quantity = purchaseBindingModel.getQuantity();
        double totalAmount = purchaseBindingModel.getPrice() * quantity;
        String ageName = purchaseBindingModel.getAge();
        Age age = ageRepository.findByAge(ageName);
        Status status = statusRepository.getByName(StatusName.CART.getDisplayValue());
        UserEntity user = userRepository.findByEmail(userEmail).get();
        Optional<Purchase> existPurchase = purchaseRepository.getByProductAndUserAndAgeAndStatus(product, user, ageName, status);
        ProductAge productAndAge = productAgeRepository.getByProductAndAge(product, age);
        Purchase purchase;

        if(existPurchase.isEmpty()){
            purchase = new Purchase();
            purchase.setCount(quantity);
            purchase.setTotalAmount(totalAmount);
            purchase.setProduct(product);
            purchase.setAge(ageName);
            purchase.setStatus(status);
            purchase.setUser(user);
        } else {
            purchase = existPurchase.get();
            int currQuantity = purchase.getCount();
            purchase.setCount(currQuantity + quantity);
            double currPrice =  purchase.getTotalAmount();
            purchase.setTotalAmount(currPrice + totalAmount);
        }

        if(purchase.getCount() > productAndAge.getCount()){
            return null;
        }

        purchaseRepository.save(purchase);

        return purchase;
    }

    @Override
    public Set<Purchase> getAllPurchasesByUserEmail(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail).get();
        Status status = statusRepository.getByName(StatusName.CART.getDisplayValue());
        return purchaseRepository.getAllByUserAndStatus(user, status);
    }
}
