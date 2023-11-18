package com.app.ModernKids.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class AddProductBindingModel {
    private String name;
    private Double price;
   // @FileAnnotation(contentTypes = {"image/png", "image/jpeg"})
    private MultipartFile picture;
    private String brand;
    private int count;
    private String origin;

    private List<String> ages;
    private String category;
    private String type;

}
