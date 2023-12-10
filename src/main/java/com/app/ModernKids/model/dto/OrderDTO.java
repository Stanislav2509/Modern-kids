package com.app.ModernKids.model.dto;

import com.app.ModernKids.model.entity.Purchase;
import com.app.ModernKids.model.entity.Status;
import com.app.ModernKids.model.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Double price;
    private LocalDate date;
    private Status status;
    private Set<Purchase> purchases;
    private UserEntity user;
}
