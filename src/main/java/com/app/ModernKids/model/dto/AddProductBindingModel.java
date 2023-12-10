package com.app.ModernKids.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class AddProductBindingModel {
    @Column(unique = true)
    @Size(min= 3, max=50)
    private String name;
    @NotNull
    @Min(1)
    private Double price;

   // @FileAnnotation(contentTypes = {"image/png", "image/jpeg"})
    private MultipartFile picture;
    @Size(min=3, max = 20)
    private String brand;
    @NotNull
    private Integer count;
    @Size(min=3, max = 20)
    private String origin;
    @NotBlank
    private String  age;
    @NotBlank
    private String category;
    @NotBlank
    private String type;
    @NotBlank
    private String collection;
}
