package com.app.ModernKids.controller;

import com.app.ModernKids.model.dto.*;
import com.app.ModernKids.model.entity.*;
import com.app.ModernKids.model.view.ProductViewModel;
import com.app.ModernKids.service.ProductAgeService;
import com.app.ModernKids.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    private final ProductService productService;
    private final AgeService ageService;
    private final TypeProductService typeProductService;
    private final CategoryService categoryService;
   private final PurchaseService purchaseService;
   private final ProductAgeService productAgeService;
   private final CollectionService collectionService;

    public ProductController(ProductService productService, AgeService ageService,
                             TypeProductService typeProductService, CategoryService categoryService,
                             PurchaseService purchaseService, ProductAgeService productAgeService, CollectionService collectionService) {
        this.productService = productService;
        this.ageService = ageService;
        this.typeProductService = typeProductService;
        this.categoryService = categoryService;
        this.purchaseService = purchaseService;
        this.productAgeService = productAgeService;
        this.collectionService = collectionService;
    }

    @GetMapping("/add-product")
    public ModelAndView addProduct(@ModelAttribute("addProductBindingModel") AddProductBindingModel addProductBindingModel){
        ModelAndView modelAndView = new ModelAndView("add-product");
        List<ProductAgeDTO> productAgeDTOS=  productAgeService.getAll();
        List<Age> ages = ageService.getAll();
        List<TypeProduct> types = typeProductService.getAll();
        List<Category> categories = categoryService.getAll();
        List<Collection> collections = collectionService.getAll();

        modelAndView.addObject("productsAges", productAgeDTOS);
        modelAndView.addObject("ages", ages);
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("collections", collections);
        return modelAndView;
    }


    @PostMapping("/add-product")
    public ModelAndView addProduct(@ModelAttribute("addProductBindingModel") @Valid AddProductBindingModel addProductBindingModel,
                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("add-product");
            List<ProductAgeDTO> productAgeDTOS=  productAgeService.getAll();
            List<Age> ages = ageService.getAll();
            List<TypeProduct> types = typeProductService.getAll();
            List<Category> categories = categoryService.getAll();

            modelAndView.addObject("productsAges", productAgeDTOS);
            modelAndView.addObject("ages", ages);
            modelAndView.addObject("types", types);
            modelAndView.addObject("categories", categories);
            return modelAndView;
        }

        productService.add(addProductBindingModel);
        return new ModelAndView("redirect:/add-product");

    }

    @GetMapping("/view-products/{categoryId}")
    public ModelAndView viewProducts(@PathVariable("categoryId") Long categoryId,
                                     @ModelAttribute("filterProductBindingModel") FilterProductBindingModel filterProductBindingModel){
        ModelAndView modelAndView = new ModelAndView("view-products");
        List<ProductViewModel> products = productService.getAllByCategoryId(categoryId);
        List<ProductViewModel> filterProducts = productService.filter(products, filterProductBindingModel);
        List<String> brands = productService.getBrands();
        List<String> origins = productService.getOrigins();
        List<Age> ages = ageService.getAll();
        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("categoryID", categoryId);
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("products", filterProducts);
        modelAndView.addObject("brands", brands);
        modelAndView.addObject("origins", origins);
        modelAndView.addObject("ages", ages);

        return modelAndView;
    }

    @GetMapping("/view-products/{categoryId}/{typeId}")
    public ModelAndView viewBabyGirlKits( @PathVariable("categoryId") Long categoryId,
                                          @PathVariable("typeId") Long typeId,
                                          @ModelAttribute("filterProductBindingModel") FilterProductBindingModel filterProductBindingModel){
        ModelAndView modelAndView = new ModelAndView("view-products");
        TypeProduct type = typeProductService.getById(typeId);
        Category category = categoryService.getById(categoryId);
        List<String> brands = productService.getBrands();
        List<String> origins = productService.getOrigins();
        List<Age> ages = ageService.getAll();
        List<ProductViewModel> products = productService.getAllByTypeAndCategory(type, category);
        List<ProductViewModel> filterProducts = productService.filter(products, filterProductBindingModel);
        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();
        modelAndView.addObject("categoryID", categoryId);
        modelAndView.addObject("typeID", typeId);
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("products", filterProducts);
        modelAndView.addObject("brands", brands);
        modelAndView.addObject("origins", origins);
        modelAndView.addObject("ages", ages);
        return modelAndView;
    }

    @GetMapping("/view-collection/{collectionName}")
    public ModelAndView viewProductsByCollection(@PathVariable("collectionName") String collectionName,
                                     @ModelAttribute("filterProductBindingModel") FilterProductBindingModel filterProductBindingModel){
        ModelAndView modelAndView = new ModelAndView("view-collection");

        List<ProductViewModel> products = productService.getAllByCollection(collectionName);
        List<ProductViewModel> filterProducts = productService.filter(products, filterProductBindingModel);
        List<String> brands = productService.getBrands();
        List<String> origins = productService.getOrigins();
        List<Age> ages = ageService.getAll();
        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();
        modelAndView.addObject("collectionName", collectionName);
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("products", filterProducts);
        modelAndView.addObject("brands", brands);
        modelAndView.addObject("origins", origins);
        modelAndView.addObject("ages", ages);

        return modelAndView;
    }

    @GetMapping("/curr-product/{productId}")
    public ModelAndView viewCurrProduct(
            @ModelAttribute("purchaseBindingModel") PurchaseBindingModel purchaseBindingModel,
            @PathVariable("productId") Long productId ){
        ModelAndView modelAndView = new ModelAndView("curr-product");

        Product product = productService.getById(productId);
        List<Age> ageList = productAgeService.getAgesOnProduct(product);
        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject(product);
        modelAndView.addObject(ageList);

        return modelAndView;
    }

    @PostMapping("/purchase")
    public ModelAndView viewCurrProduct(
            @ModelAttribute("purchaseBindingModel") @Valid PurchaseBindingModel purchaseBindingModel,
            BindingResult bindingResult, Principal principal){
        ModelAndView modelAndView = new ModelAndView("curr-product");
        String userEmail = principal.getName();
        Product product = productService.getById(purchaseBindingModel.getProductId());
        Purchase purchase = purchaseService.getPurchase(product, purchaseBindingModel, userEmail);
        if(purchase == null){
            modelAndView.addObject("countError", true);
        }

        List<Age> ageList = productAgeService.getAgesOnProduct(product);
        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject(product);
        modelAndView.addObject(ageList);

        return modelAndView;
    }


    @GetMapping("/cart")
    public ModelAndView purchase(@ModelAttribute("purchaseBindingModel")  PurchaseBindingModel purchaseBindingModel, Principal principal){
        ModelAndView modelAndView =new ModelAndView("cart");
        String userEmail = principal.getName();
        List<PurchaseDTO> purchases = purchaseService.getPurchases(userEmail);
        double cartPrice = purchaseService.getCartPrice(purchases);

        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("purchases",purchases);
        modelAndView.addObject("cartPrice",cartPrice);
        return  modelAndView;
    }

    @DeleteMapping("/delete-purchase/{id}")
    public ModelAndView delete (@PathVariable("id") Long id) {
        purchaseService.delete(id);
        return new ModelAndView("redirect:/cart");
       // return new ModelAndView("redirect:/routes/details/" + routeId);
    }

    @GetMapping("/update-product/{id}")
    public ModelAndView updateProduct(@PathVariable("id") Long id,
                                          @ModelAttribute("updateProductBindingModel") UpdateProductBindingModel updateProductBindingModel){
        ModelAndView modelAndView = new ModelAndView("update-product");
        Product product = productService.getById(id);

        if(product == null){
            return new ModelAndView("redirect:/add-product");
        }


        Map<String, Integer> ageCount = productAgeService.getAgeCount(product);

        List<ProductDTO> products = productService.getAll();
        List<Age> ages = ageService.getAll();
        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();

        modelAndView.addObject("products", products);
        modelAndView.addObject("ages", ages);
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("product", product);
        modelAndView.addObject("ageCount", ageCount);
        return modelAndView;
    }

    @PatchMapping("/update-product/{id}")
    public ModelAndView updateProduct(@PathVariable("id") Long id,
                                          @ModelAttribute("updateProductBindingModel") @Valid UpdateProductBindingModel updateProductBindingModel,
                                          BindingResult bindingResult){
        Product product = productService.getById(id);
        if(bindingResult.hasErrors()){
            return new ModelAndView("update-product")
                    .addObject("id", id).addObject("product", product);
        }

        productService.updateById(id, updateProductBindingModel);
        return new ModelAndView("redirect:/add-product");
    }

}
