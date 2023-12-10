package com.app.ModernKids.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String message;
}
