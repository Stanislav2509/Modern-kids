package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.AddProductBindingModel;
import com.app.ModernKids.model.entity.Age;
import com.app.ModernKids.model.entity.Category;
import com.app.ModernKids.model.entity.Product;
import com.app.ModernKids.model.entity.TypeProduct;
import com.app.ModernKids.model.view.ProductViewModel;
import com.app.ModernKids.repo.AgeRepository;
import com.app.ModernKids.repo.CategoryRepository;
import com.app.ModernKids.repo.ProductRepository;
import com.app.ModernKids.repo.TypeProductRepository;
import com.app.ModernKids.service.ProductService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String BASE_IMAGES_PATH = ".\\src\\main\\resources\\static\\images\\";
    private final ProductRepository productRepository;
    private final TypeProductRepository typeProductRepository;
    private final CategoryRepository categoryRepository;
    private final AgeRepository ageRepository;

    public ProductServiceImpl(ProductRepository productRepository, TypeProductRepository typeProductRepository, CategoryRepository categoryRepository, AgeRepository ageRepository) {
        this.productRepository = productRepository;
        this.typeProductRepository = typeProductRepository;
        this.categoryRepository = categoryRepository;
        this.ageRepository = ageRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void add(AddProductBindingModel addProductBindingModel) {
        Product product = new Product();
        product.setName(addProductBindingModel.getName());
        product.setBrand(addProductBindingModel.getBrand());
        product.setOrigin(addProductBindingModel.getOrigin());
        product.setCount(addProductBindingModel.getCount());
        product.setPrice(addProductBindingModel.getPrice());

        TypeProduct type = typeProductRepository.findByName(addProductBindingModel.getType());
        product.setType(type);

        Category category = categoryRepository.findByName(addProductBindingModel.getCategory());
        product.setCategory(category);

        MultipartFile pictureFile = addProductBindingModel.getPicture();
        boolean isUpload = uploadPicture(pictureFile, pictureFile.getOriginalFilename());

        if(isUpload){
            product.setImageURL(pictureFile.getOriginalFilename());
        }

        List<String> agesBinding = addProductBindingModel.getAges();
        Set<Age> ages = new HashSet<>();

        for (String currAge: agesBinding) {
            Age age = ageRepository.findByAge(currAge);
            ages.add(age);
        }

        product.setAges(ages);

        productRepository.save(product);
    }

    @Override
    public List<ProductViewModel> getAllViewModel() {
        List<Product> products = productRepository.findAll();
        List<ProductViewModel> productViewModels = new ArrayList<>();

        for (Product product : products) {
            ProductViewModel productViewModel = new ProductViewModel();
            productViewModel.setName(product.getName());
            productViewModel.setCount(product.getCount());
            productViewModel.setOrigin(product.getOrigin());
            productViewModel.setBrand(product.getBrand());
            productViewModel.setPrice(product.getPrice());
            productViewModel.setImageURL(product.getImageURL());
            Optional<TypeProduct> type = typeProductRepository.findById(product.getType().getId());
            type.ifPresent(typeProduct -> productViewModel.setType(typeProduct.getName()));

            Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
            category.ifPresent(value -> productViewModel.setCategory(value.getName()));
            List<String> agesName = new ArrayList<>();
            Set<Age> ages = product.getAges();
            for (Age age : ages) {
                Optional<Age> currAge = ageRepository.findById(age.getId());
                currAge.ifPresent(value -> agesName.add(value.getAge()));
            }
            productViewModel.setAges(agesName);
            productViewModels.add(productViewModel);
        }
        return productViewModels;
    }

    private boolean uploadPicture(MultipartFile file, String filePath){
        try {
            File newFile = new File(BASE_IMAGES_PATH + filePath);
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();

            OutputStream outputStream = new FileOutputStream(newFile);
            outputStream.write(file.getBytes());

            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
