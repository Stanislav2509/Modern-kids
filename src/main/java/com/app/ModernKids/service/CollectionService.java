package com.app.ModernKids.service;

import com.app.ModernKids.model.dto.AddCollectionBindingModel;
import com.app.ModernKids.model.entity.Collection;

import java.util.List;

public interface CollectionService {
    List<Collection> getAll();

    void add(AddCollectionBindingModel addCollectionBindingModel);

    boolean deleteById(Long id);

    Collection getById(Long id);

    void updateById(Long id, AddCollectionBindingModel addCollectionBindingModel);

}
