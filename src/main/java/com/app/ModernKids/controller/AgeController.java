package com.app.ModernKids.controller;

import com.app.ModernKids.model.dto.AddAgeBindingModel;
import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.service.AgeService;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
            ModelAndView modelAndView = new ModelAndView("add-age");
            List<Age> ages = ageService.getAll();
            modelAndView.addObject("ages", ages);
            return modelAndView;
        }

        ageService.add(addAgeBindingModel);
        return new ModelAndView("redirect:/add-age");
    }

    @DeleteMapping("/delete-age/{id}")
    public ModelAndView deleteAge(@PathVariable("id") Long id,
                                  @ModelAttribute("addAgeBindingModel") AddAgeBindingModel addAgeBindingModel){
        ModelAndView modelAndView = new ModelAndView("add-age");

        boolean isSuccessfullyDeleted = ageService.deleteById(id);

        List<Age> ages = ageService.getAll();
        modelAndView.addObject("ages", ages);

        if(isSuccessfullyDeleted){
            modelAndView.addObject("hasDeletedError", false);
         return modelAndView;
        }

        modelAndView.addObject("hasDeletedError", true);
        return modelAndView;
    }

    @GetMapping("/update-age/{id}")
    public ModelAndView updateAge(@PathVariable("id") Long id,
                                  @ModelAttribute("addAgeBindingModel") AddAgeBindingModel addAgeBindingModel){
        ModelAndView modelAndView = new ModelAndView("update-age");
        Age age = ageService.getAgeById(id);

        if(age == null){
            return new ModelAndView("redirect:/add-age");
        }

        modelAndView.addObject("age", age);
        return modelAndView;
    }

    @PatchMapping("/update-age/{id}")
    public ModelAndView updateAge(@PathVariable("id") Long id,
                                  @ModelAttribute("addAgeBindingModel") @Valid AddAgeBindingModel addAgeBindingModel,
                                  BindingResult bindingResult){
        Age age = ageService.getAgeById(id);
        if(bindingResult.hasErrors()){
           return new ModelAndView("update-age").addObject("id", id).addObject("age", age);
        }

        ageService.updateById(id, addAgeBindingModel);
        return new ModelAndView("redirect:/add-age");
    }
}
