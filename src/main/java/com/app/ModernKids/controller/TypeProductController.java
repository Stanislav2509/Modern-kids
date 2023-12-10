package com.app.ModernKids.controller;

import com.app.ModernKids.model.dto.AddAgeBindingModel;
import com.app.ModernKids.model.dto.AddTypeProductBindingModel;
import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.service.TypeProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
        ModelAndView modelAndView = new ModelAndView("add-type-product");
        if(bindingResult.hasErrors()){
            List<TypeProduct> typeProducts = typeProductService.getAll();
            modelAndView.addObject("types", typeProducts);
            return modelAndView;
        }

        typeProductService.add(addTypeProductBindingModel);
        return new ModelAndView("redirect:/add-type-product");
    }

    @DeleteMapping("/delete-type-product/{id}")
    public ModelAndView deleteTYpe(@PathVariable("id") Long id,
                                  @ModelAttribute("addTypeProductBindingModel") AddTypeProductBindingModel addTypeProductBindingModel){
        ModelAndView modelAndView = new ModelAndView("add-type-product");

        boolean isSuccessfullyDeleted = typeProductService.deleteById(id);

        List<TypeProduct> types = typeProductService.getAll();
        modelAndView.addObject("types", types);

        if(isSuccessfullyDeleted){
            modelAndView.addObject("hasDeletedError", false);
            return modelAndView;
        }

        modelAndView.addObject("hasDeletedError", true);
        return modelAndView;
    }

    @GetMapping("/update-type-product/{id}")
    public ModelAndView updateTypeProduct(@PathVariable("id") Long id,
                                  @ModelAttribute("addTypeProductBindingModel") AddTypeProductBindingModel addTypeProductBindingModel){
        ModelAndView modelAndView = new ModelAndView("update-type-product");
        TypeProduct typeProduct = typeProductService.getById(id);

        if(typeProduct == null){
            return new ModelAndView("redirect:/add-type-product");
        }

        modelAndView.addObject("typeProduct", typeProduct);
        return modelAndView;
    }

    @PatchMapping("/update-type-product/{id}")
    public ModelAndView updateTypeProduct(@PathVariable("id") Long id,
                                  @ModelAttribute("addTypeProductBindingModel") @Valid AddTypeProductBindingModel addTypeProductBindingModel,
                                  BindingResult bindingResult){
        TypeProduct typeProduct = typeProductService.getById(id);
        if(bindingResult.hasErrors()){
            return new ModelAndView("update-type-product")
                    .addObject("id", id).addObject("typeProduct", typeProduct);
        }

        typeProductService.updateById(id, addTypeProductBindingModel);
        return new ModelAndView("redirect:/add-type-product");
    }
}
