package com.app.ModernKids.controller;

import com.app.ModernKids.model.dto.AddAgeBindingModel;
import com.app.ModernKids.service.AgeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AgeController {
    private final AgeService ageService;

    public AgeController(AgeService ageService) {
        this.ageService = ageService;
    }

    @GetMapping("/add-age")
    public ModelAndView addAge(@ModelAttribute("addAgeBindingModel") AddAgeBindingModel addAgeBindingModel){
        ModelAndView modelAndView = new ModelAndView("add-age");
        List<com.app.ModernKids.model.entity.Age> ages = ageService.getAll();
        modelAndView.addObject("ages", ages);
        return modelAndView;
    }

    @PostMapping("/add-age")
    public ModelAndView addAge(@ModelAttribute("addAgeBindingModel") @Valid AddAgeBindingModel addAgeBindingModel,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("redirect:/add-age");
        }

        ageService.add(addAgeBindingModel);
        return new ModelAndView("redirect:/add-age");
    }
}
