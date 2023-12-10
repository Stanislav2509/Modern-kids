package com.app.ModernKids.controller;

import com.app.ModernKids.model.dto.QueryBindingModel;
import com.app.ModernKids.model.dto.QueryDTO;
import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.model.enums.StatusName;
import com.app.ModernKids.service.CategoryService;
import com.app.ModernKids.service.QueryService;
import com.app.ModernKids.service.TypeProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class QueryController {
    private final QueryService queryService;
    private final TypeProductService typeProductService;
    private final CategoryService categoryService;

    public QueryController(QueryService queryService, TypeProductService typeProductService, CategoryService categoryService) {
        this.queryService = queryService;
        this.typeProductService = typeProductService;
        this.categoryService = categoryService;
    }

    @GetMapping("/contact-form")
    public ModelAndView contactForm(@ModelAttribute("queryBindingModel") QueryBindingModel queryBindingModel){
        ModelAndView modelAndView = new ModelAndView("contact-form");
        Map<String, List<TypeProduct>> types = typeProductService.getTypes();
        List<Category> categories = categoryService.getAll();
        modelAndView.addObject("types", types);
        modelAndView.addObject("categories", categories);

        return modelAndView;
    }

    @PostMapping("/contact-form")
    public ModelAndView contactForm(@ModelAttribute("queryBindingModel") @Valid QueryBindingModel queryBindingModel,
                                    BindingResult bindingResult){
        queryService.saveQuery(queryBindingModel);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/queries")
    public ModelAndView queries(){
        List<QueryDTO> queries = queryService.getByStatus(StatusName.NEW.getDisplayValue());
        return new ModelAndView("queries").addObject("queries",queries);
    }

    @PatchMapping("/queries{id}")
    public ModelAndView queries(@PathVariable("id") Long id){
        queryService.updateStatus(id, StatusName.ANSWERED.getDisplayValue());
        return new ModelAndView("redirect:/queries");
    }

}
