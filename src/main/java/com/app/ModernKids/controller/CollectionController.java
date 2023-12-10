package com.app.ModernKids.controller;

import com.app.ModernKids.model.dto.AddAgeBindingModel;
import com.app.ModernKids.model.dto.AddCollectionBindingModel;
import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.model.entity.Collection;
import com.app.ModernKids.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CollectionController {
    private  final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/add-collection")
    public ModelAndView addCollection(@ModelAttribute("addCollectionBindingModel") AddCollectionBindingModel addCollectionBindingModel){
        ModelAndView modelAndView = new ModelAndView("add-collection");
        List<Collection> collections = collectionService.getAll();
        modelAndView.addObject("collections", collections);
        return modelAndView;
    }

    @PostMapping("/add-collection")
    public ModelAndView addCollection(@ModelAttribute("addCollectionBindingModel") @Valid AddCollectionBindingModel addCollectionBindingModel,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("add-collection");
            List<Collection> collections = collectionService.getAll();
            modelAndView.addObject("collections", collections);
            return modelAndView;
        }

        collectionService.add(addCollectionBindingModel);
        return new ModelAndView("redirect:/add-collection");
    }

    @DeleteMapping("/delete-collection/{id}")
    public ModelAndView deleteCollection(@PathVariable("id") Long id,
                                  @ModelAttribute("addCollectionBindingModel") AddCollectionBindingModel addCollectionBindingModel){
        ModelAndView modelAndView = new ModelAndView("add-collection");

        boolean isSuccessfullyDeleted = collectionService.deleteById(id);

        List<Collection> collections = collectionService.getAll();
        modelAndView.addObject("collections", collections);

        if(isSuccessfullyDeleted){
            modelAndView.addObject("hasDeletedError", false);
            return modelAndView;
        }

        modelAndView.addObject("hasDeletedError", true);
        return modelAndView;
    }

    @GetMapping("/update-collection/{id}")
    public ModelAndView updateCollection(@PathVariable("id") Long id,
                                  @ModelAttribute("addCollectionBindingModel") AddCollectionBindingModel addCollectionBindingModel){
        ModelAndView modelAndView = new ModelAndView("update-collection");
        Collection collection = collectionService.getById(id);

        if(collection == null){
            return new ModelAndView("redirect:/add-collection");
        }

        modelAndView.addObject("collection", collection);
        return modelAndView;
    }

    @PatchMapping("/update-collection/{id}")
    public ModelAndView updateCollection(@PathVariable("id") Long id,
                                  @ModelAttribute("addCollectionBindingModel") @Valid AddCollectionBindingModel addCollectionBindingModel,
                                  BindingResult bindingResult){
        Collection collection = collectionService.getById(id);
        if(bindingResult.hasErrors()){
            return new ModelAndView("update-collection")
                    .addObject("id", id).addObject("collection", collection);
        }

        collectionService.updateById(id, addCollectionBindingModel);
        return new ModelAndView("redirect:/add-collection");
    }


}
