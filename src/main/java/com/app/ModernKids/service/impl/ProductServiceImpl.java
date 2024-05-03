package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.AddProductBindingModel;
import com.app.ModernKids.model.dto.FilterProductBindingModel;
import com.app.ModernKids.model.dto.ProductDTO;
import com.app.ModernKids.model.dto.UpdateProductBindingModel;
import com.app.ModernKids.model.entity.*;
import com.app.ModernKids.model.entity.Collection;
import com.app.ModernKids.model.view.ProductViewModel;
import com.app.ModernKids.repo.*;
import com.app.ModernKids.service.ProductService;
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
    private final PurchaseRepository purchaseRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final ProductAgeRepository productAgeRepository;
    private final CollectionRepository collectionRepository;

    public ProductServiceImpl(ProductRepository productRepository, TypeProductRepository typeProductRepository, CategoryRepository categoryRepository, AgeRepository ageRepository, PurchaseRepository purchaseRepository, StatusRepository statusRepository, UserRepository userRepository, ProductAgeRepository productAgeRepository, CollectionRepository collectionRepository) {
        this.productRepository = productRepository;
        this.typeProductRepository = typeProductRepository;
        this.categoryRepository = categoryRepository;
        this.ageRepository = ageRepository;
        this.purchaseRepository = purchaseRepository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
        this.productAgeRepository = productAgeRepository;
        this.collectionRepository = collectionRepository;
    }

    @Override
    public List<ProductDTO> getAll() {
        List<ProductDTO> products = new ArrayList<>();

        for (Product product : productRepository.findAll()) {
            ProductDTO productDTO  = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setImageURL(product.getImageURL());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            Set<ProductAge> productAges = product.getProductAges();

            for (ProductAge productAge : productAges) {
                productDTO.setAge(productAge.getAge().getAge());
                productDTO.setCount(productAge.getCount());
            }

            products.add(productDTO);
        }

        return products;
    }

    @Override
    public void add(AddProductBindingModel addProductBindingModel) {

        Product product = productRepository.findByName(addProductBindingModel.getName());
        if(product == null){
            product = new Product();
            product.setName(addProductBindingModel.getName());
            product.setBrand(addProductBindingModel.getBrand());
            product.setOrigin(addProductBindingModel.getOrigin());
            product.setPrice(addProductBindingModel.getPrice());
            TypeProduct type = typeProductRepository.findByName(addProductBindingModel.getType());
            product.setType(type);
            Category category = categoryRepository.findByName(addProductBindingModel.getCategory());
            product.setCategory(category);
            Collection collection = collectionRepository.getByName(addProductBindingModel.getCollection());
            product.setCollection(collection);

            MultipartFile pictureFile = addProductBindingModel.getPicture();
            boolean isUpload = uploadPicture(pictureFile, pictureFile.getOriginalFilename());

            if(isUpload){
                String originalFilename = pictureFile.getOriginalFilename();
                product.setImageURL(originalFilename);
            }

            productRepository.save(product);
        }

        String ageName = addProductBindingModel.getAge();
        Age age = ageRepository.findByAge(ageName);
        ProductAge productAge = new ProductAge();
        productAge.setProduct(product);
        productAge.setAge(age);
        productAge.setCount(addProductBindingModel.getCount());

        productAgeRepository.save(productAge);

    }

    @Override
    public List<ProductViewModel> getAllByTypeAndCategory(TypeProduct type, Category category) {
        List<Product> products = productRepository.findAllByTypeAndCategory(type, category);
        return mapProductViewModels(products);
    }



    @Override
    public List<ProductViewModel> getAllByCategoryId(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        List<Product> products = new ArrayList<>();
        if(category.isPresent()){
             products= productRepository.findAllByCategory(category.get());
        }
        return mapProductViewModels(products);
    }

    @Override
    public Product getById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElse(null);
    }


    @Override
    public List<String> getBrands() {
        return productRepository.findAllBrand();
    }

    @Override
    public List<String> getOrigins() {
        return productRepository.findAllOrigin();
    }

    @Override
    public List<ProductViewModel> filter(List<ProductViewModel> products, FilterProductBindingModel filterProductBindingModel) {
        List<ProductViewModel> correctProducts = new ArrayList<>();

        if(filterProductBindingModel.isMakeClear()){
            filterProductBindingModel.clear();
        }

        for (ProductViewModel product : products) {
           // boolean isCorrectProduct = true;
            List<String> brands = filterProductBindingModel.getBrands();
            List<String> filterAges = filterProductBindingModel.getAges();
            List<String> origins = filterProductBindingModel.getOrigins();
            int minPrice = filterProductBindingModel.getMinPrice();
            int maxPrice = filterProductBindingModel.getMaxPrice();

            if(brands != null && !brands.isEmpty() &&  !brands.contains(product.getBrand())) {
                //isCorrectProduct = false;
                continue;
            }

             List<String> productAges = product.getAges();

            boolean isCorrectAge = true;
            for (String productAge : productAges) {
                if(filterAges != null && !filterAges.isEmpty() && !filterAges.contains(productAge)) {
                    // isCorrectProduct = false;
                    isCorrectAge = false;
                }else {
                    isCorrectAge = true;
                    break;
                }
            }

            if(!isCorrectAge){
                continue;
            }



            if(origins != null && !origins.isEmpty() && !origins.contains(product.getOrigin())) {
                // isCorrectProduct = false;
                continue;
            }

            if(minPrice != 0 && maxPrice != 0 && (product.getPrice() < minPrice ||
                    product.getPrice() > maxPrice)){
                continue;
            }

            correctProducts.add(product);
        }
        return correctProducts;
    }

    @Override
    public void updateById(Long id, UpdateProductBindingModel updatePictureBindingModel) {
        Optional<Product> productOpt = productRepository.findById(id);

        Product product = productOpt.get();
        product.setPrice(updatePictureBindingModel.getPrice());
        List<ProductAge> productAges = productAgeRepository.getProductAgesByProduct(product);
        List<String> ages = updatePictureBindingModel.getAges();
        List<Integer> counts = updatePictureBindingModel.getCounts();

        for (int i = 0; i < ages.size(); i++) {
            ProductAge productAge = productAges.get(i);
            String age =productAge.getAge().getAge();
            if(ages.get(i).equals(age)) {
                productAge.setCount(counts.get(i));
            }
        }

        String newAge = updatePictureBindingModel.getNewAge();
        if(newAge != null) {
            ProductAge productAge = new ProductAge();
            productAge.setCount(updatePictureBindingModel.getCount());
            Age age = ageRepository.findByAge(newAge);
            productAge.setAge(age);
            productAge.setProduct(product);
            productAges.add(productAge);
        }
        productAgeRepository.saveAll(productAges);

        Set<ProductAge> newProductAges = new HashSet<>(productAges);

        product.setProductAges(newProductAges);

        productRepository.save(product);
    }

    @Override
    public List<ProductViewModel> getAllByCollection(String collectionName) {
        Collection collection = collectionRepository.getByName(collectionName);
        List<Product> products = productRepository.findAllByCollection(collection);
        return mapProductViewModels(products);
    }

    private boolean uploadPicture(MultipartFile file, String filePath){
        try {
            File newFile = new File(BASE_IMAGES_PATH + (filePath));
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
    private List<ProductViewModel> mapProductViewModels(List<Product> products) {
        List<ProductViewModel> productViewModels = new ArrayList<>();

        for (Product product : products) {
            ProductViewModel productViewModel = new ProductViewModel();
            productViewModel.setId(product.getId());
            productViewModel.setName(product.getName());
            productViewModel.setOrigin(product.getOrigin());
            productViewModel.setBrand(product.getBrand());
            productViewModel.setPrice(product.getPrice());
            productViewModel.setImageURL(product.getImageURL());
            Optional<TypeProduct> currType = typeProductRepository.findById(product.getType().getId());
            currType.ifPresent(typeProduct -> productViewModel.setType(typeProduct.getName()));

            Optional<Category> currCategory = categoryRepository.findById(product.getCategory().getId());
            currCategory.ifPresent(value -> productViewModel.setCategory(value.getName()));
            List<ProductAge> productAges = productAgeRepository.getProductAgesByProduct(product);
            List<String> agesName = new ArrayList<>();

            for (ProductAge productAge : productAges) {
                Optional<Age> currAge = ageRepository.findById(productAge.getAge().getId());
                currAge.ifPresent(value -> agesName.add(value.getAge()));
            }
            productViewModel.setAges(agesName);
            productViewModels.add(productViewModel);
        }
        return productViewModels;
    }

    private static String fixName(String name){
        char[] chars = name.toCharArray();
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if( (ch >= 65 && ch <= 90) || (ch > 97 && ch <= 122) || ch == 46) {
                res.append(ch);
            }
        }

        return res.toString();
    }
}
