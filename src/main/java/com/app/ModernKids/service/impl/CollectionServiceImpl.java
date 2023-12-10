package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.AddCollectionBindingModel;
import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.model.entity.Collection;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.ProductAge;
import com.app.ModernKids.repo.CollectionRepository;
import com.app.ModernKids.repo.ProductRepository;
import com.app.ModernKids.service.CollectionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;
    private final ProductRepository productRepository;

    public CollectionServiceImpl(CollectionRepository collectionRepository, ProductRepository productRepository) {
        this.collectionRepository = collectionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Collection> getAll() {
        return collectionRepository.findAll();
    }

    @Override
    public void add(AddCollectionBindingModel addCollectionBindingModel) {
        Collection collection = new Collection();
        collection.setName(addCollectionBindingModel.getName());
        collectionRepository.save(collection);
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Collection> collectionOpt = collectionRepository.findById(id);
        if(collectionOpt.isPresent()) {
            Collection collection  = collectionOpt.get();
            List<Product> products = productRepository.getByCollection(collection);

            if(!products.isEmpty()){
                return false;
            }

            collectionRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public Collection getById(Long id) {
        Optional<Collection> byId = collectionRepository.findById(id);

        return byId.orElse(null);
    }

    @Override
    public void updateById(Long id, AddCollectionBindingModel addCollectionBindingModel) {
        Optional<Collection> byId = collectionRepository.findById(id);

        Collection collection = byId.get();
        collection.setName(addCollectionBindingModel.getName());
        collectionRepository.save(collection);
    }
}
