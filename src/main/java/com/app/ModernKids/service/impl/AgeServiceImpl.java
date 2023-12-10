package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.AddAgeBindingModel;
import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.model.entity.ProductAge;
import com.app.ModernKids.repo.AgeRepository;
import com.app.ModernKids.repo.ProductAgeRepository;
import com.app.ModernKids.service.AgeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgeServiceImpl implements AgeService {
    private final AgeRepository ageRepository;
    private final ProductAgeRepository productAgeRepository;

    public AgeServiceImpl(AgeRepository ageRepository, ProductAgeRepository productAgeRepository) {
        this.ageRepository = ageRepository;
        this.productAgeRepository = productAgeRepository;
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

    @Override
    public boolean deleteById(Long id) {
        Optional<Age> ageOpt = ageRepository.findById(id);
        if(ageOpt.isPresent()) {
            Age age = ageOpt.get();
            List<ProductAge> productAges = productAgeRepository.getByAge(age);

            if(!productAges.isEmpty()){
                return false;
            }

            ageRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public void updateById(Long id, AddAgeBindingModel addAgeBindingModel) {
        Optional<Age> ageOpt = ageRepository.findById(id);

        Age age = ageOpt.get();
                age.setAge(addAgeBindingModel.getAge());
                ageRepository.save(age);
    }

    @Override
    public Age getAgeById(Long id) {
        Optional<Age> age = ageRepository.findById(id);
        return age.orElse(null);
    }


}
