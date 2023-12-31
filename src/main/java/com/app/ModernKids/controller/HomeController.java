package com.app.ModernKids.controller;

import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.model.enums.CategoryName;
import com.app.ModernKids.service.CategoryService;
import com.app.ModernKids.service.TypeProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private final TypeProductService typeProductService;
    private final CategoryService categoryService;

    public HomeController(TypeProductService typeProductService, CategoryService categoryService) {
        this.typeProductService = typeProductService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("home");
        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }
}
