package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.PurchaseBindingModel;
import com.app.ModernKids.model.dto.PurchaseDTO;
import com.app.ModernKids.model.entity.*;
import com.app.ModernKids.model.enums.StatusName;
import com.app.ModernKids.repo.*;
import com.app.ModernKids.service.PurchaseService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final ProductAgeRepository productAgeRepository;
    private final AgeRepository ageRepository;
    private final ProductRepository productRepository;
    private final List<PurchaseBindingModel> cart = new ArrayList<>();


    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, UserRepository userRepository, StatusRepository statusRepository, ProductAgeRepository productAgeRepository, AgeRepository ageRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.productAgeRepository = productAgeRepository;
        this.ageRepository = ageRepository;
        this.productRepository = productRepository;
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
    public void addToCart(PurchaseBindingModel purchaseBindingModel) {
        boolean isExist = false;
        for(int i = 0; i < cart.size(); i++) {
            PurchaseBindingModel currPurchase = cart.get(i);
            if(Objects.equals(currPurchase.getProductId(), purchaseBindingModel.getProductId()) &&
                currPurchase.getAge().equals(purchaseBindingModel.getAge())) {

                currPurchase.setQuantity(currPurchase.getQuantity() + purchaseBindingModel.getQuantity());
                currPurchase.setTotalAmount(currPurchase.getQuantity() * currPurchase.getPrice());
                isExist = true;
            }
        }

        if(!isExist) {
            purchaseBindingModel.setTotalAmount(purchaseBindingModel.getQuantity() * purchaseBindingModel.getPrice());
            cart.add(purchaseBindingModel);
        }
    }

    @Override
    public Set<Purchase> getAllPurchasesByUserEmail(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail).get();
        Status status = statusRepository.getByName(StatusName.CART.getDisplayValue());
        return purchaseRepository.getAllByUserAndStatus(user, status);
    }

    @Override
    public List<PurchaseDTO> getProductsInCart() {
        List<PurchaseDTO> purchaseDTOS = new ArrayList<>();
        for (PurchaseBindingModel purchase : cart) {
            Optional<Product> product = productRepository.findById(purchase.getProductId());
            PurchaseDTO purchaseDTO = new PurchaseDTO();
            purchaseDTO.setProduct(product.orElse(null));
            purchaseDTO.setCount(purchase.getQuantity());
            purchaseDTO.setTotalAmount(purchase.getTotalAmount());
            purchaseDTO.setAge(purchase.getAge());
            purchaseDTOS.add(purchaseDTO);
        }
        return purchaseDTOS;
    }

    @Override
    public void delete(Long id, String age) {
        cart.removeIf(purchase -> Objects.equals(purchase.getProductId(), id) && purchase.getAge().equals(age));
    }

    @Override
    public void clearCart() {
        cart.clear();
    }

    @Override
    public void delete(Long id) {
     //   cart.removeIf(purchase -> Objects.equals(purchase.getProductId(), id));
    }
}
