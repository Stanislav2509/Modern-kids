package com.app.ModernKids.controller;

import com.app.ModernKids.model.dto.AddProductBindingModel;
import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.model.view.ProductViewModel;
import com.app.ModernKids.service.AgeService;
import com.app.ModernKids.service.CategoryService;
import com.app.ModernKids.service.ProductService;
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
public class ProductController {
    private final ProductService productService;
    private final AgeService ageService;
    private final TypeProductService typeProductService;
    private final CategoryService categoryService;


    public ProductController(ProductService productService, AgeService ageService, TypeProductService typeProductService, CategoryService categoryService) {
        this.productService = productService;
        this.ageService = ageService;
        this.typeProductService = typeProductService;
        this.categoryService = categoryService;
    }

    @GetMapping("/add-product")
    public ModelAndView addProduct(@ModelAttribute("addProductBindingModel") AddProductBindingModel addProductBindingModel){
        ModelAndView modelAndView = new ModelAndView("add-product");
        List<Product> products = productService.getAll();
        List<Age> ages = ageService.getAll();
        List<TypeProduct> types = typeProductService.getAll();
        List<Category> categories = categoryService.getAll();

        modelAndView.addObject("products", products);
        modelAndView.addObject("ages", ages);
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }


    @PostMapping("/add-product")
    public ModelAndView addProduct(@ModelAttribute("addProductBindingModel") @Valid AddProductBindingModel addProductBindingModel,
                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("redirect:/add-product");
        }

        productService.add(addProductBindingModel);
        return new ModelAndView("redirect:/add-product");

    }

    @GetMapping("/view-products")
    public ModelAndView viewProducts(){
        ModelAndView modelAndView = new ModelAndView("view-products");
        List<ProductViewModel> products = productService.getAllViewModel();
        modelAndView.addObject("products", products);

        return modelAndView;
    }

    @GetMapping("/baby-girl-kits")
    public ModelAndView viewBabyGirlKits(){
        ModelAndView modelAndView = new ModelAndView("baby-girl-kits");

        return modelAndView;
    }
}
