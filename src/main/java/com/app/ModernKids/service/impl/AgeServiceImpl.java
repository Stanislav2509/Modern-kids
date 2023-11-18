package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.AddAgeBindingModel;
import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.repo.AgeRepository;
import com.app.ModernKids.service.AgeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeServiceImpl implements AgeService {
    private final AgeRepository ageRepository;

    public AgeServiceImpl(AgeRepository ageRepository) {
        this.ageRepository = ageRepository;
    }

    @Override
    public List<Age> getAll() {
        return ageRepository.findAll();
    }

    @Override
    public boolean isUniqueAge(String age) {
        return ageRepository.findByAge(age) == null;
    }

    @Override
    public void add(AddAgeBindingModel addAgeBindingModel) {
        Age age = new Age();
        age.setAge(addAgeBindingModel.getAge());
        ageRepository.save(age);
    }
}
