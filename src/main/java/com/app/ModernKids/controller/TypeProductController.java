package com.app.ModernKids.controller;

import com.app.ModernKids.model.dto.AddTypeProductBindingModel;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.service.TypeProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TypeProductController {
    private final TypeProductService typeProductService;

    public TypeProductController(TypeProductService typeProductService) {
        this.typeProductService = typeProductService;
    }

    @GetMapping("/add-type-product")
    public ModelAndView addTypeProduct(@ModelAttribute("addTypeProductBindingModel") AddTypeProductBindingModel addTypeProductBindingModel){
        ModelAndView modelAndView = new ModelAndView("add-type-product");
        List<TypeProduct> typeProducts = typeProductService.getAll();
        modelAndView.addObject("types", typeProducts);
        return modelAndView;
    }

    @PostMapping("/add-type-product")
    public ModelAndView addTypeProduct(@ModelAttribute("addTypeProductBindingModel") @Valid AddTypeProductBindingModel addTypeProductBindingModel,
                                       BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ModelAndView("redirect:/add-type-product");
        }

        typeProductService.add(addTypeProductBindingModel);
        return new ModelAndView("redirect:/add-type-product");
    }
}
