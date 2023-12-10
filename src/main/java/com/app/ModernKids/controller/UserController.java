package com.app.ModernKids.controller;

import com.app.ModernKids.model.dto.UserRegisterBindingModel;
import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.service.CategoryService;
import com.app.ModernKids.service.TypeProductService;
import com.app.ModernKids.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private final UserService userService;
    private final TypeProductService typeProductService;
    private final CategoryService categoryService;

    public UserController(UserService userService, TypeProductService typeProductService, CategoryService categoryService) {
        this.userService = userService;
        this.typeProductService = typeProductService;
        this.categoryService = categoryService;
    }

    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegisterBindingModel")
                                     UserRegisterBindingModel userRegisterBindingModel){
        ModelAndView modelAndView = new ModelAndView("register");
        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegisterBindingModel") @Valid
                                     UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("register");
        }

        boolean hasSuccessfullyRegistration = userService.register(userRegisterBindingModel);

        if(!hasSuccessfullyRegistration){
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("hasRegistrationError", true);
            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView("login");
        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);

        return modelAndView;
    }

    @PostMapping("/login-error")
    public ModelAndView failLogin(@ModelAttribute("email") String email){
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("email", email);
        modelAndView.addObject("bad_credentials", "true");

        return modelAndView;
    }
}
