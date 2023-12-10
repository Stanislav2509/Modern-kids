package com.app.ModernKids.model.view;

import com.app.ModernKids.model.entity.Age;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductViewModel {
    private Long id;
    private String name;
    private Double price;
    private String imageURL;
    private String brand;
    private int count;
    private String origin;
    private List<String> ages;
    private String category;
    private String type;
}
