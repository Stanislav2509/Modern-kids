package com.app.ModernKids.service;

import com.app.ModernKids.model.dto.AddAgeBindingModel;
import com.app.ModernKids.model.entity.Age;

import java.util.List;

public interface AgeService {
    List<Age> getAll();

    boolean isUniqueAge(String age);

    void add(AddAgeBindingModel addAgeBindingModel);
}
