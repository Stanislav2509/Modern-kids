package com.app.ModernKids.model.dto;

import com.app.ModernKids.model.entity.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryBindingModel {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String message;
}
