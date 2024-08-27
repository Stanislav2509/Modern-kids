package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.OrderDTO;
import com.app.ModernKids.model.dto.PurchaseDTO;
import com.app.ModernKids.model.entity.*;
import com.app.ModernKids.model.enums.StatusName;
import com.app.ModernKids.repo.*;
import com.app.ModernKids.service.OrderService;
import com.app.ModernKids.service.PurchaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final StatusRepository statusRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final ProductAgeRepository productAgeRepository;
    private final AgeRepository ageRepository;
    private final PurchaseService purchaseService;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, StatusRepository statusRepository, PurchaseRepository purchaseRepository, ProductRepository productRepository, ProductAgeRepository productAgeRepository, AgeRepository ageRepository, PurchaseService purchaseService, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.statusRepository = statusRepository;
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.productAgeRepository = productAgeRepository;
        this.ageRepository = ageRepository;
        this.purchaseService = purchaseService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean makeOrder(String userEmail) {
        Set<Purchase> purchaseSet = purchaseService.getAllPurchasesByUserEmail(userEmail);
        UserEntity user = userRepository.findByEmail(userEmail).get();
        double totalAmount = 0;
        List<ProductAge> productAgeList = new ArrayList<>();
        for (Purchase purchase : purchaseSet) {
            Optional<Product> productOpt = productRepository.findById(purchase.getProduct().getId());
            Age age = ageRepository.findByAge(purchase.getAge());

            if(productOpt.isPresent()){
                Product product = productOpt.get();
                ProductAge productAge = productAgeRepository.getByProductAndAge(product, age);

                if(productAge.getCount() < purchase.getCount()){
                    return false;
                }

                totalAmount += purchase.getTotalAmount();
                productAge.setCount(productAge.getCount() - purchase.getCount());
                productAgeList.add(productAge);
            }
        }

        Order order = new Order();
        order.setPurchases(purchaseSet);
        order.setPrice(totalAmount);
        order.setDate(LocalDate.now());
        Status statusNew = statusRepository.getByName(StatusName.NEW.getDisplayValue());
        order.setStatus(statusNew);
        order.setUser(user);
        orderRepository.save(order);

        for (Purchase purchase: purchaseSet) {
            Status statusBought = statusRepository.getByName(StatusName.BOUGHT.getDisplayValue());
            purchase.setStatus(statusBought);
            purchase.setOrder(order);
            purchaseRepository.save(purchase);
        }

        productAgeRepository.saveAll(productAgeList);

        return true;
    }

    @Override
    public void updateStatus(Long id, String statusName ) {
        Status statusCourier = statusRepository.getByName(statusName);
        Optional<Order> orderOptional= orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();;
            order.setStatus(statusCourier);
            orderRepository.save(order);
        }
    }

    @Override
    public List<OrderDTO> getOrderByStatus(String statusName) {
        Status status = statusRepository.getByName(statusName);
        List<Order> orders= orderRepository.getByStatus(status);
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setPrice(order.getPrice());
            orderDTO.setUser(order.getUser());
            orderDTO.setStatus(order.getStatus());
            orderDTO.setPurchases(order.getPurchases());
            orderDTO.setDate(order.getDate());
            orderDTOS.add(orderDTO);
        }

        return orderDTOS;
    }

    @Override
    public void makeOrder() {
        List<PurchaseDTO> cart = purchaseService.getProductsInCart();
//        Set<Purchase> purchaseSet = purchaseService.getAllPurchasesByUserEmail(userEmail);
//        UserEntity user = userRepository.findByEmail(userEmail).get();
        double totalAmount = 0;
        List<ProductAge> productAgeList = new ArrayList<>();
        for (PurchaseDTO purchase : cart) {
            Optional<Product> productOpt = productRepository.findById(purchase.getProduct().getId());
            Age age = ageRepository.findByAge(purchase.getAge());

            if(productOpt.isPresent()){
                Product product = productOpt.get();
                ProductAge productAge = productAgeRepository.getByProductAndAge(product, age);

//                if(productAge.getCount() < purchase.getCount()){
//                    return false;
//                }

                totalAmount += purchase.getTotalAmount();
                productAge.setCount(productAge.getCount() - purchase.getCount());
                productAgeList.add(productAge);
            }
        }

        Set<Purchase> purchases = new LinkedHashSet<>();

        for (PurchaseDTO curr: cart) {
            Purchase purchase = new Purchase();
            purchase.setAge(curr.getAge());
            purchase.setProduct(curr.getProduct());
            purchase.setTotalAmount(curr.getTotalAmount());
            purchase.setCount(curr.getCount());
            purchases.add(purchase);
        }

        Order order = new Order();
        order.setPurchases(purchases);
        order.setPrice(totalAmount);
        order.setDate(LocalDate.now());
        Status statusNew = statusRepository.getByName(StatusName.NEW.getDisplayValue());
        order.setStatus(statusNew);
       // order.setUser(user);
        orderRepository.save(order);

        purchaseService.clearCart();

        for (Purchase purchase: purchases) {
//            Status statusBought = statusRepository.getByName(StatusName.BOUGHT.getDisplayValue());
//            purchase.setStatus(statusBought);
            purchase.setOrder(order);
            purchaseRepository.save(purchase);
        }

        productAgeRepository.saveAll(productAgeList);



    }

}
