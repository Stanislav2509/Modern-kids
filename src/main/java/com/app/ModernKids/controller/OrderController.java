package com.app.ModernKids.controller;

import com.app.ModernKids.model.dto.OrderDTO;
import com.app.ModernKids.model.dto.SessionUserBindingModel;
import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.model.enums.StatusName;
import com.app.ModernKids.service.CategoryService;
import com.app.ModernKids.service.OrderService;
import com.app.ModernKids.service.TypeProductService;
import com.app.ModernKids.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final TypeProductService typeProductService;
    private final CategoryService categoryService;
    private final UserService userService;

    public OrderController(OrderService orderService, TypeProductService typeProductService, CategoryService categoryService, UserService userService) {
        this.orderService = orderService;
        this.typeProductService = typeProductService;
        this.categoryService = categoryService;
        this.userService = userService;
    }
    @GetMapping("/delivery-data")
    public ModelAndView deliveryDataSessionUser(@ModelAttribute("sessionUserBindingModel")
                                                SessionUserBindingModel sessionUserBindingModel) {
        return new ModelAndView("session-user-data");
    }

    @PostMapping("/delivery-data")
    public ModelAndView deliveryDataSessionUser(@ModelAttribute("sessionUserBindingModel") @Valid
                                                SessionUserBindingModel sessionUserBindingModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ModelAndView("session-user-data");
        }
        userService.register(sessionUserBindingModel);

        return new ModelAndView("redirect:/buy");
    }

    @GetMapping("/buy")
    public ModelAndView buy(Principal principal){
        ModelAndView modelAndView = new ModelAndView("completed-order");


        if(principal != null) {
            String userEmail = principal.getName();
            orderService.makeOrder(userEmail);
        }else {
            orderService.makeOrder();
        }


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
