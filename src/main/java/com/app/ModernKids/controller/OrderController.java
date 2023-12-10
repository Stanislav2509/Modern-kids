package com.app.ModernKids.controller;

import com.app.ModernKids.model.dto.OrderDTO;
import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.model.enums.StatusName;
import com.app.ModernKids.service.CategoryService;
import com.app.ModernKids.service.OrderService;
import com.app.ModernKids.service.TypeProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final TypeProductService typeProductService;
    private final CategoryService categoryService;

    public OrderController(OrderService orderService, TypeProductService typeProductService, CategoryService categoryService) {
        this.orderService = orderService;
        this.typeProductService = typeProductService;
        this.categoryService = categoryService;
    }

    @PostMapping("/buy")
    public ModelAndView buy(Principal principal){
        ModelAndView modelAndView = new ModelAndView("completed-order");
        String userEmail = principal.getName();
         orderService.makeOrder(userEmail);

        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/new-orders")
    public ModelAndView newOrders(){
        ModelAndView modelAndView = new ModelAndView("new-orders");
        List<OrderDTO> newOrders = orderService.getOrderByStatus(StatusName.NEW.getDisplayValue());
        List<OrderDTO> sendByCourierOrders = orderService.getOrderByStatus(StatusName.SENT_BY_COURIER.getDisplayValue());

        modelAndView.addObject("newOrders", newOrders);
        modelAndView.addObject("sendByCourierOrders", sendByCourierOrders);

        return modelAndView;
    }

    @PatchMapping("/courier-order/{id}")
    public ModelAndView newOrder(@PathVariable("id") Long id){

        orderService.updateStatus(id, StatusName.SENT_BY_COURIER.getDisplayValue());

        return new ModelAndView("redirect:/new-orders");
    }

    @PatchMapping("/completed-order/{id}")
    public ModelAndView completedOrder(@PathVariable("id") Long id){

        orderService.updateStatus(id, StatusName.COMPLETED.getDisplayValue());

        return new ModelAndView("redirect:/new-orders");
    }

    @PatchMapping("/failed-order/{id}")
    public ModelAndView failedOrder(@PathVariable("id") Long id){

        orderService.updateStatus(id, StatusName.FAILED.getDisplayValue());

        return new ModelAndView("redirect:/new-orders");
    }
}
